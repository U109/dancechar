                                  高频面试题：如何基于JWT实现用户身份认证
一、常见的场景及解决方案
常见的场景：
账号/密码登录、手机号验证码登录、微信扫码登录。

解决方案：
1、基于session认证方案
1）什么是session认证？
服务端生成httpsession(内存-sessionId)----->sessionId写到浏览器cookie-->浏览器请求的header中自动带sessionId到服务端
--->服务端校验sessionId是否合法
2）优缺点
优点：方案成熟，实现简单。
缺点：
. 服务端压力大。用户的信息在保存在服务端的内存，用户量越大，内存开销越大。
. 扩展性差。用户信息存在某一个服务器上，应用节点就会有状态，分布式环境下，无法做到水平无限扩展。
  如何解决这个痛点呢？分布式节点下session共享，比如将session信息存储到redis，以java为例，可以集成spring-session-data-redis二方包
. 普通的session认证不支持跨域。因为session需要配合cookie才能实现，由于cookie默认不支持跨域访问，所以，当涉及到前端跨域请求后端接
  口的时候，需要做很多额外的配置，才能实现跨域session认证。
. 容易被类似的CSRF攻击。因为是基于浏览器的cookie来进行用户识别的, cookie如果被截获，用户就会很容易受到跨站请求伪造的攻击。
适用场景：单节点部署应用，如果多节点部署情况下使用session共享/负载均衡端会话保持（SLB设置）
2、基于JWT认证方案
1）什么是JWT？
JWT(JSON Web Token)是目前最流行的跨域认证解决方案，是一种基于Token的认证授权机制。JWT自身包含了身份验证所需要的所有信息,
因此，我们的服务器不需要存储Session信息。这显然增加了系统的可用性和伸缩性，大大减轻了服务端的压力。
2）JWT格式及组成
jwt也就是令牌的token，是一个String字符串，由三部分组成，中间用点隔开，连接在一起就一个JWT token，就像这样：
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9.TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
jwt token令牌组成：
标头（Header）
有效载荷（Payload）
签名（Signature）
token格式：head.payload.Signature 如：xxxxx.yyyy.zzzz
详细解释如下所示：
Header：描述JWT的元数据，定义了生成签名的算法以及Token的类型。如HMAC、SHA256、RSA；使用Base64编码，如下：
{
"alg" : "HS256",
"type" : "JWT"
}
以上经过base64后值：eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
Payload：有效负载，用来存放实际需要传递的数据，千万不要放用户敏感的信息，如密码。同样使用Base64编码，如下：
{
"sub" : "123",
"name" : "John Do",
"admin" : true
}
以上经过base64后值：eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9
Signature ：前面两部分都使用Base64进行编码，前端可以解开知道里面的信息。Signature需要使用编码后的header和payload
加上我们提供的一个密钥，使用header中指定的签名算法(HS256)进行签名。签名的作用是保证JWT没有被篡改过。
HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret);
处理完后值：TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ
注意：这里的secret私钥保存在服务端，非常的重要千万不能泄露，建议私钥做成可配置的，定期修改secret的值。
3）优缺点
优点：
. 跨平台实现, token是加密的形式保存在客户端，与语言无关，原则上任何web形式都支持。
. 不需要存储Session，服务端节点可水平无限扩展。
. 不依赖Cookie,使得其可以防止CSRF攻击。
. 性能好，只需要在header中携带token，即可实现认证，传递数据少。
缺点：
. jwt生成的token在有效期内-直可用，因为主要存在客户端，无法在服务端删除。
. 用户登出，只能在客户端localStorage删除token，无法在服务端控制。
. jwt本身无法实现用户禁止登录或拉黑用户，都需要业务自己实现。
适用场景：分布式集群

二、 分布式微服务如何基于JWT进行身份验证
参考：JWT认证鉴权交互流程.png

三、扩展面试题
. jwt token如何进行续期
1）管理后台
服务端（系统认证鉴权服务）：
登录接口：返回accessToken和refreshToken（管理后台：accessToken一般设置为30分钟，refreshToken1小时，小程序或APP：
accessToken一般设置为7天，refreshToken 30天）
token续期接口：通过前端传过来的refreshToken，获取新的token（这里如果refreshToken过期，直接提示用户重新登录）
前端：
1）前端将accessToken和refreshToken存到浏览器缓存
2）请求业务接口header中Authorization参数携带token，token以bear：开头
3）如果接口返回token过期，前端通过refreshToken请求token续期接口，返回新的accessToken
4）前端将新的accessToken更新缓存，下次请求业务接口采用新的accessToken

. jwt token如何实现拉黑
服务端（系统认证鉴权服务）：通过管理后台，将指定的用户拉黑/剔除
提供的服务能力：
拉黑接口：将拉黑用户信息放到redis
剔除黑名单接口：更新用户信息为非黑户
判断黑户接口：从redis中获取用户信息判断
前端：
1）黑户登录直接限制住
2）拉黑前登录，应用网关通过解析accessToken，获取用户Id信息，请求服务端判断是否黑户
3）返回指定的code和msg

四、代码演示
整体框架：
spring cloud alibaba + nacos +  mybatis-plus
实现功能：
   jwt token用户认证、jwt token续期、jwt用户拉黑
涉及项目：
dancechar-gateway（应用网关，基于spring cloud gateway）
  SecurityFilter.java 全局拦截器，处理黑名单url、token有效期、token是否过期
dancechar-system-service（系统认证，用户登录、token续期、黑户处理）
   用户登录-->请求接口
   用户token过期-->token续期
   用户拉黑-->token无效
