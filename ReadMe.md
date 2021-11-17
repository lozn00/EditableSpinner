#添加依赖
```groovy
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}



```
or setting.gradle
```groovy

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
            maven { url 'https://jitpack.io' }
    }
}

```
Step 2. Add the dependency
```groovy
	dependencies {
	        implementation 'com.github.qssq:EditableSpinner:v1.0'
	}
```

# 用法
```
      <lozn.spinner.EditSpinner
                           android:layout_height="wrap_content"
                    android:id="@+id/sampling_stardard_spinner"
                    android:layout_weight="1"
                    android:layout_width="0dp"
                          app:spinner_editable="true"
                    app:spinner_rebuild_Id="true"
                    app:spinner_mode="dialog"
                    app:spinner_item="@array/sampling_standard_item"
                    app:spinner_hint="@string/items"/>


```
#功能
```mode``` 支持dialog dropdown 默认dropdown 当这个下拉列表在底部的时候会网上展开
```item``` 设置默认item条目 ，这样对于纯字符的可以不需要用代码设置
```editable``` 控制是否可编辑
```spinner_rebuild_Id``` 如果viewpager fragment大量使用，则会导致hint重复问题，这是fragment的回收机制问题,给每一个分配不同id就可以解决这个问题
```spinner_bg``` spinner箭头图标样式
```spinner_gap``` 编辑框距离下拉列表图标的距离


arrays.xml
<string-array name="items">
<item>3333</item>
<item>5555</item>
</string-array>
# 自定义布局
通过xml控制布局样式也可以通过代码，
新建 edit_spinner_edit_layout
```xml

<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="0dp"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_weight="1"
    android:layout_height="wrap_content">
    <FrameLayout
        android:id="@+id/spinner_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <lozn.spinner.EditInnerSpinner
            android:id="@+id/spinner_inner"
            android:visibility="invisible"
            android:layout_gravity="bottom"
            android:spinnerMode="dropdown"
            
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@android:color/white">

        </lozn.spinner.EditInnerSpinner>
    </FrameLayout>
    <com.google.android.material.textfield.TextInputLayout
        android:background="@android:color/white"
        android:id="@+id/spinner_textinput_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:hint="TITLE">

        <EditText
            android:id="@+id/spinner_edittext"
            android:text=""
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:imeOptions="actionNone"
            android:background="@android:color/white"
            android:selectAllOnFocus="true"
            android:textSize="14sp"

            android:lines="1" />

    </com.google.android.material.textfield.TextInputLayout>

</FrameLayout>


```
温馨提示：
设置EditInnerSpinner的transnameY可控制drown弹出的位置偏移
EditInnerSpinner 必须设置不可见
EditInnerSpinner 集成类解决android 10的bug无条目时卡死问题。
```java

 editSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
            }
        });
        editSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editSpinner.setSelection(-1);
        String text = editSpinner.getText();//设置-1清空文本，  这里将得到空
        
        editSpinner.setOnValueChangeListener(new EditSpinner.OnValueChangeListener() {
            @Override
            public void onLossFocus() {
                    
            }

            @Override
            public void onLossFocusAndTextChange() {
                    //spiner失去焦点，并且内容发生改变的时候触发
            }

            @Override
            public void onTextChanged(CharSequence s) {

            }

            @Override
            public void onGainFocus() {

            }

            /**
             * 返回true更新 状态，那么基本上不会调用  onLossFocusAndTextChange
             * @param position
             * @param selectedItem
             * @return
             */
            @Override
            public boolean onItemSelectPostionChanged(int position, String selectedItem) {
                return false;
            }
        });
        
        
        


```
library inner  use library TextInputLayout 
    ```implementation 'com.google.android.material:material:1.4.0'```

#测试demo

[下载apk](https://raw.githubusercontent.com/qssq/EditableSpinner/master/apk/app.apk)
![pic1](https://raw.githubusercontent.com/qssq/EditableSpinner/master/apk/pic1.jpg)
![pic1](https://raw.githubusercontent.com/qssq/EditableSpinner/master/apk/pic2.jpg)
![pic1](https://raw.githubusercontent.com/qssq/EditableSpinner/master/apk/pic3.jpg)

