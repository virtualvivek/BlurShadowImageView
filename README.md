<h2 align="center"> <img src="apk/preview/app_icon_title.jpg" width="400" /> </h2>

<p align="center">
 
 <a href="https://android.com">
    <img src="https://camo.githubusercontent.com/b56ba74e599ebf4a6d782716b3d63fa39c7e90e2/68747470733a2f2f7777772e636c657665726f61642e636f6d2f7075626c69632f636f6d65726369616c2f6c6162656c2d616e64726f69642e737667" width="120"
      alt="Android" />
  </a>
  
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/badge/MinSdk-17-blue.svg"
      alt="MinSDK" />
  </a>
  
  <a href="https://jitpack.io/#virtualvivek/BlurShadowImageView">
    <img src="https://jitpack.io/v/virtualvivek/BlurShadowImageView.svg?color=34495e"
      alt="JitPack" />
  </a>
  
  <a href="https://github.com/virtualvivek/BlurShadowImageView/blob/master/library/src/main/java/me/virtualiz/blurshadowimageview/BlurShadowImageView.java">
    <img src="https://img.shields.io/github/size/virtualvivek/BlurShadowImageView/library/src/main/java/me/virtualiz/blurshadowimageview/BlurShadowImageView.java?color=16ab9c&label=Library%20Size"
      alt="Library Size" />
  </a>
 
  
  <a href="https://developer.android.com/about/versions/android-4.0.html">
    <img src="https://img.shields.io/github/repo-size/virtualvivek/BlurShadowImageView.svg?color=e91e63"
      alt="MinSDK" />
  </a>
  

  
</p>

<p align="center">This library provides blurred drop shadows to <b>ImageView</b> similar to <code>iOS backdrop shadows</code>.Provides fast canvas draw as no renderscript needed .The similar shadow blurred effects can also be seen in <i>iOS Music App.</i></p>

<p align="center"><img src="apk/preview/app_icon_demo.jpg" width="620" /> </p> 

## Download Demo App

Download the demo app <code><b>.apk</b></code> file here 

<a href="apk/BlurShadow.apk">
<img src="apk/preview/app_icon_demo_app.png" width="280"
      alt="Demo App" /></a>
      
## Scan to Download      
<img src="apk/app_barcode.PNG" width="180"
      alt="Demo App" />

​
## Installation
Add it in your root build.gradle at the end of repositories :
```js
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
 ```

Add the following dependency to your app build.gradle file :
```js
dependencies {
	        implementation 'com.github.virtualvivek:BlurShadowImageView:1.12'
	}
 ```

​
## How to use   
### Using Xml to config
```xml

 <me.virtualiz.blurshadowimageview.BlurShadowImageView
                android:layout_width="200dp"
                android:layout_height="220dp"
                android:layout_gravity="center"
                app:v_shadowOffset="40dp"
                app:v_imageRound="20dp"
                app:v_imageSrc="@drawable/nature" />
```

###  Use Java code to config
```js
BlurShadowImageView blurshadowimageview = (BlurShadowImageView)findViewById(R.id.blurSImageView);

//Sets Border Round Radius
blurshadowimageview.setRound((int) value);  

//Sets Image Resource
blurshadowimageview.setImageResource(ImgRes);  

//Sets Image Drawable
blurshadowimageview.setImageDrawable(drawable);  

//Sets Image Bitmap
blurshadowimageview.setImageBitmap(bitmap);  

```

​
##  Load image with Picasso

<img align="right" src="apk/preview/app_load_online.gif" width="280" />


```js
 Picasso.with(getBaseContext())
	 .load("https://i.imgur.com/DvpvklR.png")
	 .into(new Target() {
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
	demo_img.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable drawable) {
	//on failed do something
    }

    @Override
    public void onPrepareLoad(Drawable drawable) {
	//on prepare loading do something
    }
});			
```


​
## Image Blur Backdrop Offset

<img align="left" src="apk/preview/app_preview_offset.jpg" width="650" />

<p align="right"><br><br><br><br><br><br><br><br><br><br><br><br></p>


​
## Documentation 

 |Name|Format|Default|Details|
 |:---:|:---:|:---:|:---:|
 |app:v_imageSrc    |reference|image|sets image to the ImageView|
 |app:v_imageRound  |dimension|10dp|sets border radius to the ImageView|
 |app:v_shadowOffset|dimension|40dp|configure the distance between the Image and the Shadow|
 

## License

BlurShadowImageView is licensed under `MIT license`. View [license](https://github.com/virtualvivek/BlurShadowImageView/blob/master/LICENSE).<br>
Copyright (c) 2020 ` Vivek Verma .`
