# 自定义注解处理器

注入过程
* 1、通过ViewInject注解标识一些View的成员变量；
* 2、通过ViewInjectProcessor捕获添加了ViewInject注解的元素，并且按照宿主类进行分类；
* 3、为每个含有ViewInject注解的宿主类生成一个InjectAdapter辅助类，并且在它的Inject函数中生成初始化View的代码；
* 4、在SimpleDagger的Inject函数中构成生成辅助类，此时内部会调用这个InjectAdapter辅助类对象的Inject函数，这个函数有会初始化宿主类中的View的成员变量，至此，View就已经被初始化啦。

