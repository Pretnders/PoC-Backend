package pretnders.api.profile_pictures.domain.ports.out

interface HandleProfilePicturesOut {
    fun getNextPicOrder(pretnderId:Long):Long
    fun addPic(pretnderReference:String, pictureReference:String, profilePicUrl:String)
    fun swapPicturesOrder(swapperReference:String, swapperOrder:Short, swappedReference:String, swappedOrder:Short)
    fun deletePicture(pictureReference:String)
    fun updatePictureUrl(pictureReference:String, newUrl:String)

}