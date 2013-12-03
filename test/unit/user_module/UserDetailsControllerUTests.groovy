package user_module



import org.junit.*
import grails.test.mixin.*

@TestFor(UserDetailsController)
@Mock(UserDetails)
class UserDetailsControllerUTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/userDetails/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.userDetailsInstanceList.size() == 0
        assert model.userDetailsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.userDetailsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.userDetailsInstance != null
        assert view == '/userDetails/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/userDetails/show/1'
        assert controller.flash.message != null
        assert UserDetails.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/userDetails/list'

        populateValidParams(params)
        def userDetails = new UserDetails(params)

        assert userDetails.save() != null

        params.id = userDetails.id

        def model = controller.show()

        assert model.userDetailsInstance == userDetails
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/userDetails/list'

        populateValidParams(params)
        def userDetails = new UserDetails(params)

        assert userDetails.save() != null

        params.id = userDetails.id

        def model = controller.edit()

        assert model.userDetailsInstance == userDetails
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/userDetails/list'

        response.reset()

        populateValidParams(params)
        def userDetails = new UserDetails(params)

        assert userDetails.save() != null

        // test invalid parameters in update
        params.id = userDetails.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/userDetails/edit"
        assert model.userDetailsInstance != null

        userDetails.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/userDetails/show/$userDetails.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        userDetails.clearErrors()

        populateValidParams(params)
        params.id = userDetails.id
        params.version = -1
        controller.update()

        assert view == "/userDetails/edit"
        assert model.userDetailsInstance != null
        assert model.userDetailsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/userDetails/list'

        response.reset()

        populateValidParams(params)
        def userDetails = new UserDetails(params)

        assert userDetails.save() != null
        assert UserDetails.count() == 1

        params.id = userDetails.id

        controller.delete()

        assert UserDetails.count() == 0
        assert UserDetails.get(userDetails.id) == null
        assert response.redirectedUrl == '/userDetails/list'
    }
}
