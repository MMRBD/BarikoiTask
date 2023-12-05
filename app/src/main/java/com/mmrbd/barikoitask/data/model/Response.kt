package com.mmrbd.barikoitask.data.model

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("widget")
	val widget: Widget? = null
)

data class Window(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)

data class Text(

	@field:SerializedName("vOffset")
	val vOffset: Int? = null,

	@field:SerializedName("data")
	val data: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("style")
	val style: String? = null,

	@field:SerializedName("alignment")
	val alignment: String? = null,

	@field:SerializedName("onMouseUp")
	val onMouseUp: String? = null,

	@field:SerializedName("hOffset")
	val hOffset: Int? = null
)

data class Image(

	@field:SerializedName("vOffset")
	val vOffset: Int? = null,

	@field:SerializedName("src")
	val src: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("alignment")
	val alignment: String? = null,

	@field:SerializedName("hOffset")
	val hOffset: Int? = null
)

data class Widget(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("debug")
	val debug: String? = null,

	@field:SerializedName("window")
	val window: Window? = null,

	@field:SerializedName("text")
	val text: Text? = null
)
