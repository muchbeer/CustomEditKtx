package raum.muchbeer.customeditktx

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged

class EditTextSearchCustomView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle){

    init {
        setLeftDrawable(android.R.drawable.ic_menu_search)
        setTextChangeListener()
        setOnEditorActionListener()
        setDrawablesListener()
        imeOptions = EditorInfo.IME_ACTION_SEARCH

        setOnFocusChangeListener(OnFocusChangeListener { view, isFocus ->
            if(isFocus) {
              Log.d("EditTextCustom", "We have the focus already set waiting to implement")
                setLeftDrawable(0)
                setLeftDrawable(android.R.drawable.ic_input_add)
            }
        })
    }


    private var queryTextListener: QueryTextListener? = null

    private fun setTextChangeListener() {
        doAfterTextChanged {
            if (it.isNullOrBlank()) {
                setRightDrawable(0)
            } else {
                setRightDrawable(android.R.drawable.ic_menu_close_clear_cancel)
            }
            queryTextListener?.onQueryTextChange(it.toString())
        }
    }

    private fun setOnEditorActionListener() {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                queryTextListener?.onQueryTextSubmit(text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun setDrawablesListener() {
        setOnTouchListener(OnTouchListener { view, event ->
            view.performClick()
            if (event.action == MotionEvent.ACTION_UP) {
                when {
                    rightDrawableClicked(event) -> {
                        setText("")
                        return@OnTouchListener true
                    }
                    leftDrawableClicked(event) -> {
                        queryTextListener?.onQueryTextSubmit(text.toString())
                        return@OnTouchListener true
                    }
                    else -> {
                        return@OnTouchListener false
                    }
                }
            }
            false
        })
    }

    private fun rightDrawableClicked(event: MotionEvent): Boolean {

        val rightDrawable = compoundDrawables[DRAWABLE_RIGHT_INDEX]

        return if (rightDrawable == null) {
            false
        } else {
            val startOfDrawable = width - rightDrawable.bounds.width() - paddingRight
            val endOfDrawable = startOfDrawable + rightDrawable.bounds.width()
            startOfDrawable <= event.x && event.x <= endOfDrawable
        }

    }

    private fun leftDrawableClicked(event: MotionEvent): Boolean {

        val leftDrawable = compoundDrawables[DRAWABLE_LEFT_INDEX]

        return if (leftDrawable == null) {
            false
        } else {
            val startOfDrawable = paddingLeft
            val endOfDrawable = startOfDrawable + leftDrawable.bounds.width()
            startOfDrawable <= event.x && event.x <= endOfDrawable
        }

    }

    fun setQueryTextChangeListener(queryTextListener: QueryTextListener) {
        this.queryTextListener = queryTextListener
    }

    interface QueryTextListener {

        fun onQueryTextSubmit(query: String?)

        fun onQueryTextChange(newText: String?)

    }




    companion object {
        private const val DRAWABLE_LEFT_INDEX = 0
        private const val DRAWABLE_RIGHT_INDEX = 2
    }

}