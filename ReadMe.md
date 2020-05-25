<h3>app-version:</h3>

app-version策略: 通过business继承是实现，重写doDispatch，在版本号类调用交易号方法。启单个服务。

base-stable策略: 定制化方案策略，base实现基本特性、部件。stable通过继承+定制实现。启多个服务。

api-version策略：自定义ApiVersion注解，重写RequestMappingHandlerMapping，将带版本的controller拼进mapping。启单个服务。