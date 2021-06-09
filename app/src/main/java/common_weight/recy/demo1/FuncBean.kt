package common_weight.recy.demo1;

import androidx.databinding.ObservableField
import com.txx.app.main.section.clickEvent.Click

/**
 *
 * created by houyl
 * on  7:56 PM
 */
data class FuncBean(
    var items: List<Item>
)
data class Item(
        var click: Click,
        var dialog: Dialog,
        var funcId: String,
        var imgurl: String,
        var isNew: String,
        var parentId: String,
        var title: String


){
    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }
        val that = o as Item
        return funcId == that.funcId && parentId == that.parentId
    }
}


data class Dialog(
    var dialogContent: String,
    var isShow: String,
    var isEnter: String
)

/**
 *
 * created by houyl
 * on  7:56 PM
 * 处理的Item
 */
data class ProcessItemBean(
        val item: Item,
        var displayNew: Int=-1
)

class PointBean {
    var isSlected: ObservableField<Boolean>

    constructor(isSlected: Boolean, possion: Int) {
        this.isSlected = ObservableField(isSlected)
    }

    constructor(isSlected: Boolean) {
        this.isSlected = ObservableField(isSlected)
    }

    override fun toString(): String {
        return "SelectBean{" +
                "isSlected=" + isSlected +
                '}'
    }
}