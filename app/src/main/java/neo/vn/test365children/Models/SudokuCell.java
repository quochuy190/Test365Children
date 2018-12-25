package neo.vn.test365children.Models;

import android.content.Context;
import android.view.View;

public class SudokuCell extends View{
	private int value;
	private boolean modifiable = true;

	public SudokuCell(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
	
	public void setNotModifiable(){
		modifiable = false;
	}
	
	public void setInitValue(int value){
		this.value = value;
		invalidate();
	}
	
	public void setValue( int value ){
		if( modifiable ){
			this.value = value;
		}
		invalidate();
	}
	
	public int getValue(){
		return value;
	}
}
