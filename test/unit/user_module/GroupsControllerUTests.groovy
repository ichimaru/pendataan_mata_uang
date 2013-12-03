package user_module



import org.junit.*
import grails.test.mixin.*

@TestFor(GroupsController)
@Mock(Groups)
class GroupsControllerUTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/groups/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.groupsInstanceList.size() == 0
        assert model.groupsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.groupsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.groupsInstance != null
        assert view == '/groups/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/groups/show/1'
        assert controller.flash.message != null
        assert Groups.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/groups/list'

        populateValidParams(params)
        def groups = new Groups(params)

        assert groups.save() != null

        params.id = groups.id

        def model = controller.show()

        assert model.groupsInstance == groups
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/groups/list'

        populateValidParams(params)
        def groups = new Groups(params)

        assert groups.save() != null

        params.id = groups.id

        def model = controller.edit()

        assert model.groupsInstance == groups
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/groups/list'

        response.reset()

        populateValidParams(params)
        def groups = new Groups(params)

        assert groups.save() != null

        // test invalid parameters in update
        params.id = groups.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/groups/edit"
        assert model.groupsInstance != null

        groups.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/groups/show/$groups.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        groups.clearErrors()

        populateValidParams(params)
        params.id = groups.id
        params.version = -1
        controller.update()

        assert view == "/groups/edit"
        assert model.groupsInstance != null
        assert model.groupsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/groups/list'

        response.reset()

        populateValidParams(params)
        def groups = new Groups(params)

        assert groups.save() != null
        assert Groups.count() == 1

        params.id = groups.id

        controller.delete()

        assert Groups.count() == 0
        assert Groups.get(groups.id) == null
        assert response.redirectedUrl == '/groups/list'
    }
}
