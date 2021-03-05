package com.yongliang.houylbase.hilt

import androidx.appcompat.app.AppCompatActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 *
 * created by houyl
 * on  5:14 PM
 */
class Repository @Inject constructor() {

}

/**
 * 这里注意以下三点。

第一，MyViewModel的头部要为其声明@ActivityRetainedScoped注解，参照刚才组件作用域那张表，我们知道这个注解就是专门为ViewModel提供的，并且它的生命周期也和ViewModel一致。

第二，MyViewModel的构造函数中要声明@Inject注解，因为我们在Activity中也要使用依赖注入的方式获得MyViewModel的实例。

第三，MyViewModel的构造函数中要加上Repository参数，表示MyViewModel是依赖于Repository的。

接下来就很简单了，我们在MainActivity中通过依赖注入的方式得到MyViewModel的实例，然后像往常一样的方式去使用它就可以了：
 */
@ActivityRetainedScoped
class MyViewModel @Inject constructor(val repository: Repository) : ViewModel() {

}
// 示例代码
//@AndroidEntryPoint
//class MainActivity : AppCompatActivity() {
//
//    @Inject
//    lateinit var viewModel: MyViewModel
//    ...
//
//}
/**
 * 这种方式虽然可以正常工作，但有个缺点是，我们改变了获取ViewModel实例的常规方式。本来我只是想对Repository进行依赖注入的，现在连MyViewModel也要跟着一起依赖注入了。

为此，对于ViewModel这种常用Jetpack组件，Hilt专门为其提供了一种独立的依赖注入方式，也就是我们接下来要介绍的第二种方式了。
 */
//implementation 'androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha02'
//kapt 'androidx.hilt:hilt-compiler:1.0.0-alpha02'


class MyViewModel1 @ViewModelInject constructor(val repository: Repository) : ViewModel() {

}
//@AndroidEntryPoint
//class Main2Activity : AppCompatActivity() {
//
//    val viewModel: MyViewModel by lazy { ViewModelProvider(this).get(MyViewModel1::class.java) }
//
//
//}