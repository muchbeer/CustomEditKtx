package raum.muchbeer.customeditktx

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class EditTextCloseCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?
): AppCompatEditText(
    context,
    attrs
) {

var mClearButtonImage: Drawable
 // var mSearchImage : Drawable


        init {
            mClearButtonImage = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_clear_black, null
            )!!


            setOnTouchListener(object : OnTouchListener {
                override fun onTouch(view: View?, event: MotionEvent?): Boolean {

                    // Use the getCompoundDrawables()[2] expression to check
                    // if the drawable is on the "end" of text [2].
                    if ((getCompoundDrawablesRelative()[2] != null)) {

                        val clearButtonStart: Int
                        val clearButtonEnd: Int
                        var isClearButtonClicked = false


                    //    var isClearSearchClicked = false
                        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                            // If RTL, get the end of the button on the left side.
                            clearButtonEnd = mClearButtonImage.intrinsicWidth + paddingStart
                            // If the touch occurred before the end of the button,
                            // set isClearButtonClicked to true.
                            if (event!!.getX() < clearButtonEnd) {
                                isClearButtonClicked = true
                            }
                        } else {
                            // Layout is LTR.
                            // Get the start of the button on the right side.
                            clearButtonStart =
                                (width - paddingEnd - mClearButtonImage.intrinsicWidth)


                            //Get the search of the button on the left side
                          /*  clearSearchStart = (
                                    width - paddingStart - mSearchImage.intrinsicWidth
                                    )*/
                            // If the touch occurred after the start of the button,
                            // set isClearButtonClicked to true.
                            if (event!!.getX() > clearButtonStart) {
                                isClearButtonClicked = true
                            }

                        }

                        // Check for actions if the button is tapped.
                        if (isClearButtonClicked) {
                            // Check for ACTION_DOWN (always occurs before ACTION_UP).
                            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                // Switch to the black version of clear button.
                                mClearButtonImage =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.ic_clear_black, null
                                    )!!
                                showClearButton()

                            }
                            // Check for ACTION_UP.
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                // Switch to the opaque version of clear button.
                                mClearButtonImage =
                                    ResourcesCompat.getDrawable(
                                        resources,
                                        R.drawable.ic_clear_opaque, null
                                    )!!
                                // Clear the text and hide the clear button.
                                getText()!!.clear();
                                hideClearButton();
                                return true
                            }
                        } else {
                            return false
                        }
                    }

                    return false
                }

            })

/*            setOnFocusChangeListener(object : OnFocusChangeListener {
                override fun onFocusChange(view: View?, isFocus: Boolean) {
                    if(isFocus) {
                       hideClearButton()
                       if(isClearButtonContinue) {
                            showClearButton()
                        }
                    }
                }

            })*/


            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // Do nothing.
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    showClearButton()
                //    isClearButtonContinue = true

                }

                override fun afterTextChanged(p0: Editable?) {
                    // Do nothing.
                }

            })


        }


   fun showClearButton() {
        // Sets the Drawables (if any) to appear to the left of,
        // above, to the right of, and below the text.
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            //start of text
            null,
            //Top of text
            null,
            //End of text
            mClearButtonImage,
            // Below text.
            null)
                   // Below text.
    }

    fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null,null, null)
    }


}