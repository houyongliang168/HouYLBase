package com.yongliang.houylbase.hilt

import android.app.Application
import com.yongliang.houylbase.APP
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier

/**
 *
 * created by houyl
 * on  4:15 PM
 * @Module注解，表示这一个用于提供依赖注入实例的模块。
 */
@Module
@InstallIn(ActivityComponent::class)
abstract class EngineModule {
    /**
     * 首先我们要定义一个抽象函数，为什么是抽象函数呢？因为我们并不需实现具体的函数体。

    其次，这个抽象函数的函数名叫什么都无所谓，你也不会调用它，不过起个好点的名字可以有助于你的阅读和理解。

    第三，抽象函数的返回值必须是Engine，表示用于给Engine类型的接口提供实例。那么提供什么实例给它呢？抽象函数接收了什么参数，就提供什么实例给它。由于我们的卡车还比较传统，使用的仍然是燃油引擎，所以bindEngine()函数接收了GasEngine参数，也就是说，会将GasEngine的实例提供给Engine接口。

    最后，在抽象函数上方加上@Bind注解，这样Hilt才能识别它。

     */
//    @Binds
//    abstract fun bindEngine(gasEngine: GasEngine): Engine
    /**
     * 我们在EngineModule中提供了两个不同的函数，它们的返回值都是Engine。那么当在Truck中给engine字段进行依赖注入时，到底是使用bindGasEngine()函数提供的实例呢？还是使用bindElectricEngine()函数提供的实例呢？Hilt也搞不清楚了。

    因此这个问题需要借助额外的技术手段才能解决：Qualifier注解。
    Qualifier注解的作用就是专门用于解决我们目前碰到的问题，给相同类型的类或接口注入不同的实例。

     */
    @BindGasEngine
    @Binds
    abstract fun bindGasEngine(gasEngine: GasEngine): Engine

    @BindElectricEngine
    @Binds
    abstract fun bindElectricEngine(electricEngine: ElectricEngine): Engine
}

/**
 * 一个注解叫BindGasEngine，一个注解叫BindElectricEngine，这样两个注解的作用就明显区分开了。

另外，注解的上方必须使用@Qualifier进行声明，这个是毫无疑问的。至于另外一个@Retention，是用于声明注解的作用范围，
选择AnnotationRetention.BINARY表示该注解在编译之后会得到保留，但是无法通过反射去访问这个注解。这应该是最合理的一个注解作用范围。
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindGasEngine

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindElectricEngine

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideMyApplication(application: Application): APP {
        return application as APP
    }

}