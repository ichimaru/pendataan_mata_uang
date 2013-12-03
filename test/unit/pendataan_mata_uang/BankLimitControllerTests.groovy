package pendataan_mata_uang



import org.junit.*
import grails.test.mixin.*

@TestFor(BankLimitController)
@Mock(BankLimit)
class BankLimitControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bankLimit/show" == response.redirectedUrl
    }
	
//	void testShow(){
//		assert flash.message == null
//		assert response.redirectedUrl == '/bankLimit/show'
//	}

//    void testList() {
//
//        def model = controller.list()
//
//        assert model.bankLimitInstanceList.size() == 0
//        assert model.bankLimitInstanceTotal == 0
//    }
//
//    void testCreate() {
//        def model = controller.create()
//
//        assert model.bankLimitInstance != null
//    }
//
//    void testSave() {
//        controller.save()
//
//        assert model.bankLimitInstance != null
//        assert view == '/bankLimit/create'
//
//        response.reset()
//
//        populateValidParams(params)
//        controller.save()
//
//        assert response.redirectedUrl == '/bankLimit/show/1'
//        assert controller.flash.message != null
//        assert BankLimit.count() == 1
//    }
//
//    void testShow() {
//        controller.show()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/bankLimit/list'
//
//        populateValidParams(params)
//        def bankLimit = new BankLimit(params)
//
//        assert bankLimit.save() != null
//
//        params.id = bankLimit.id
//
//        def model = controller.show()
//
//        assert model.bankLimitInstance == bankLimit
//    }
//
//    void testEdit() {
//        controller.edit()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/bankLimit/list'
//
//        populateValidParams(params)
//        def bankLimit = new BankLimit(params)
//
//        assert bankLimit.save() != null
//
//        params.id = bankLimit.id
//
//        def model = controller.edit()
//
//        assert model.bankLimitInstance == bankLimit
//    }
//
//    void testUpdate() {
//        controller.update()
//
//        assert flash.message != null
//        assert response.redirectedUrl == '/bankLimit/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def bankLimit = new BankLimit(params)
//
//        assert bankLimit.save() != null
//
//        // test invalid parameters in update
//        params.id = bankLimit.id
//        //TODO: add invalid values to params object
//
//        controller.update()
//
//        assert view == "/bankLimit/edit"
//        assert model.bankLimitInstance != null
//
//        bankLimit.clearErrors()
//
//        populateValidParams(params)
//        controller.update()
//
//        assert response.redirectedUrl == "/bankLimit/show/$bankLimit.id"
//        assert flash.message != null
//
//        //test outdated version number
//        response.reset()
//        bankLimit.clearErrors()
//
//        populateValidParams(params)
//        params.id = bankLimit.id
//        params.version = -1
//        controller.update()
//
//        assert view == "/bankLimit/edit"
//        assert model.bankLimitInstance != null
//        assert model.bankLimitInstance.errors.getFieldError('version')
//        assert flash.message != null
//    }
//
//    void testDelete() {
//        controller.delete()
//        assert flash.message != null
//        assert response.redirectedUrl == '/bankLimit/list'
//
//        response.reset()
//
//        populateValidParams(params)
//        def bankLimit = new BankLimit(params)
//
//        assert bankLimit.save() != null
//        assert BankLimit.count() == 1
//
//        params.id = bankLimit.id
//
//        controller.delete()
//
//        assert BankLimit.count() == 0
//        assert BankLimit.get(bankLimit.id) == null
//        assert response.redirectedUrl == '/bankLimit/list'
//    }
}
