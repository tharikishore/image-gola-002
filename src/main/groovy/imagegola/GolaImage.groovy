package imagegola

import com.googlecode.objectify.annotation.Entity
import com.googlecode.objectify.annotation.Id
import com.googlecode.objectify.annotation.Index

@Entity
class GolaImage {

	@Id
	Long id
	
	@Index
	String imgSrc
	
	@Index
	String imgUrl
	
	@Index
	String searchKey
	
	@Index
	Long imgSize
	
	@Index
	String height
	
	@Index
	String width
	
	@Index
	String aspectRatio
}
