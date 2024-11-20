package pretnders.api.profile_pictures.domain

import io.quarkus.test.InjectMock
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.jboss.resteasy.reactive.multipart.FileUpload
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.*
import pretnders.api.azure.domain.ports.out.StorageClientOut
import pretnders.api.profile_pictures.domain.models.AddedProfilePicture
import pretnders.api.profile_pictures.domain.ports.`in`.ChangeProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.RemoveProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.`in`.HandleProfilePicturesIn
import pretnders.api.profile_pictures.domain.ports.`in`.SwapProfilePictureCommand
import pretnders.api.profile_pictures.domain.ports.out.HandleProfilePicturesOut
import pretnders.api.shared.errors.ApplicationException
import pretnders.api.shared.utils.generators.UUIDGenerator

@QuarkusTest
class ProfilePicturesServiceTest {

    @InjectMock
    private lateinit var uuidGeneratorMock: UUIDGenerator
    @Inject
    private lateinit var handleProfilePicturesIn: HandleProfilePicturesIn
    @InjectMock
    private lateinit var handleProfilePicturesOut: HandleProfilePicturesOut
    @InjectMock
    private lateinit var storageClientOut: StorageClientOut

    private val fileName = "cuteanimegirl.jpg"
    private val profilePictureUrl = "https://some-url/$fileName"
    private val phoneNumber = "0764646464"

    @Test
    @DisplayName("Should add pretnder profile picture")
    fun shouldAddPicture() {
        val fileUploadMock: FileUpload = mock(FileUpload::class.java)
        val uuidMock: UUIDGenerator = mock(UUIDGenerator::class.java)
        val pretnderReference = "456789456"
        val profilePictureReference = "123456"
        whenever(fileUploadMock.fileName()).thenReturn(fileName)
        whenever(fileUploadMock.size()).thenReturn(3000)
        whenever(uuidMock.getNewUUID()).thenReturn(profilePictureReference)
        whenever(storageClientOut.addPretnderProfilePicture(phoneNumber, fileUploadMock)).doReturn(profilePictureUrl)
        whenever(uuidGeneratorMock.getNewUUID()).doReturn(profilePictureReference)
        doNothing().whenever(handleProfilePicturesOut).addPic(pretnderReference, profilePictureReference,
            profilePictureUrl)

        val res = handleProfilePicturesIn.addProfilePicture(pretnderReference, phoneNumber, fileUploadMock)

        verify(fileUploadMock).fileName()
        verify(fileUploadMock).size()
        verify(storageClientOut).addPretnderProfilePicture(phoneNumber, fileUploadMock)
        verify(handleProfilePicturesOut).addPic(pretnderReference, profilePictureReference, profilePictureUrl)
        assert(AddedProfilePicture::class.java.name == res.javaClass.name )
        assert(profilePictureReference == res.reference )
        assert(profilePictureUrl == res.url )
    }

    @ParameterizedTest
    @ValueSource(longs = [9000,12000,125000, Long.MAX_VALUE])
    @DisplayName("Should throw invalid file size exception")
    fun shouldThrowFileSizeException(fileSize: Long) {
        val fileUploadMock: FileUpload = mock(FileUpload::class.java)
        val pretnderReference = "456789456"
        whenever(fileUploadMock.fileName()).thenReturn(fileName)
        whenever(fileUploadMock.size()).thenReturn(fileSize)

        assertThrows<ApplicationException> {
           handleProfilePicturesIn.addProfilePicture(pretnderReference, phoneNumber, fileUploadMock)
        }
        verify(fileUploadMock).fileName()
        verify(fileUploadMock).size()

    }

    @ParameterizedTest
    @ValueSource(strings = ["cuteanimegirl.pdf","cuteanimegirl.exe","cuteanimegirl.gif"])
    @DisplayName("Should throw invalid file type exception")
    fun shouldThrowFileTypeException(toReturn: String) {
        val fileUploadMock: FileUpload = mock(FileUpload::class.java)
        val pretnderReference = "456789456"
        whenever(fileUploadMock.fileName()).thenReturn(toReturn)

        assertThrows<ApplicationException> {
            handleProfilePicturesIn.addProfilePicture(pretnderReference, phoneNumber, fileUploadMock)
        }
        verify(fileUploadMock).fileName()
        verifyNoMoreInteractions(fileUploadMock)
    }

    @Test
    @DisplayName("Should throw exception if pictures have same order")
    fun shouldThrowApplicationExceptionWhenOrdersAreEquals() {
        val swapperOrder:Short = 5
        val swappedOrder:Short = 6
        val swapperReference = "123456789"
        val swappedReference = "987654321"
        val command = SwapProfilePictureCommand(
            swapperReference,
            swapperOrder,
            swappedReference,
            swappedOrder
        )
        assertThrows<ApplicationException> {
            handleProfilePicturesIn.swapPicturesOrder(command)
        }
    }
    @Test
    @DisplayName("Should throw invalid order exception (value too low)")
    fun shouldThrowApplicationExceptionWhenOrdersAreBelowZero() {
        val swapperOrder = Short.MIN_VALUE
        val swappedOrder:Short = -5
        val swapperReference = "123456789"
        val swappedReference = "987654321"
        val command = SwapProfilePictureCommand(
            swapperReference,
            swapperOrder,
            swappedReference,
            swappedOrder
        )
        assertThrows<ApplicationException> {
            handleProfilePicturesIn.swapPicturesOrder(command)
        }
    }

    @Test
    @DisplayName("Should throw invalid order exception (value too high)")
    fun shouldThrowApplicationExceptionWhenOrdersAreAboveNine() {
        val swapperOrder:Short = Short.MAX_VALUE
        val swappedOrder:Short = 12
        val swapperReference = "123456789"
        val swappedReference = "987654321"
        val command = SwapProfilePictureCommand(
            swapperReference,
            swapperOrder,
            swappedReference,
            swappedOrder
        )
        assertThrows<ApplicationException> {
            handleProfilePicturesIn.swapPicturesOrder(command)
        }
    }

    @Test
    @DisplayName("Should swap orders")
    fun shouldSwapPicturesOrders() {
        val swapperOrder:Short = 2
        val swappedOrder:Short = 4
        val swapperReference = "123456789"
        val swappedReference = "987654321"
        val command = SwapProfilePictureCommand(
            swapperReference,
            swapperOrder,
            swappedReference,
            swappedOrder
        )
        doNothing().whenever(handleProfilePicturesOut).swapPicturesOrder(command.swapperReference, command
            .swapperOrder, command.swappedReference, command.swappedOrder)
        handleProfilePicturesIn.swapPicturesOrder(command)
        verify(handleProfilePicturesOut).swapPicturesOrder(swapperReference, swapperOrder, swappedReference,swappedOrder)
    }

    @Test
    @DisplayName("Should delete picture")
    fun shouldDeletePicture() {
        val pictureReference = "123456789"
        val phoneNumber = "0712121212"
        val blobName = "cuteanimegirl.jpg"
        val command = RemoveProfilePictureCommand(
            pictureReference,
            blobName,
            phoneNumber
        )
        doNothing().whenever(storageClientOut).deleteBlobFromContainer(command.phoneNumber, command.blobName)
        doNothing().whenever(handleProfilePicturesOut).deletePicture(command.profilePictureReference)
        handleProfilePicturesIn.removeProfilePicture(command)
        verify(handleProfilePicturesOut).deletePicture(command.profilePictureReference)
        verify(storageClientOut).deleteBlobFromContainer(command.phoneNumber, command.blobName)

    }

    @Test
    @DisplayName("Should change picture")
    fun shouldChangePicture() {
        val argumentCaptor = argumentCaptor<String>()
        val file = mock(FileUpload::class.java)
        val pictureReference = "123456789"
        val phoneNumber = "0712121212"
        val blobName = "cuteanimegirl.jpg"
        val url = "https://some-url.com"
        val command = ChangeProfilePictureCommand(
            pictureReference,
            blobName,
            file,
            phoneNumber
        )
        doNothing().whenever(storageClientOut).deleteBlobFromContainer(command.phoneNumber, command.blobName)
        whenever(storageClientOut.addPretnderProfilePicture(command.phoneNumber, command.file)).thenReturn(url)
        doNothing().whenever(handleProfilePicturesOut).updatePictureUrl(command.phoneNumber, url)

       val returnedUrl =  handleProfilePicturesIn.changeProfilePicture(command)
        verify(handleProfilePicturesOut).updatePictureUrl(argumentCaptor.capture(), argumentCaptor.capture())
        verify(storageClientOut).deleteBlobFromContainer(command.phoneNumber, command.blobName)
        verify(storageClientOut).addPretnderProfilePicture(command.phoneNumber, command.file)
        assert(url == returnedUrl)
        assert(pictureReference == argumentCaptor.firstValue)
        assert(url == argumentCaptor.secondValue)
    }
}