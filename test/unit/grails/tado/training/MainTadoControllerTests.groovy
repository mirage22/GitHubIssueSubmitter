package grails.tado.training

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

@TestFor(MainTadoController)
@Mock(MainTado)
class MainTadoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/mainTado/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.mainTadoInstanceList.size() == 0
        assert model.mainTadoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.mainTadoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.mainTadoInstance != null
        assert view == '/mainTado/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/mainTado/show/1'
        assert controller.flash.message != null
        assert MainTado.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/mainTado/list'

        populateValidParams(params)
        def mainTado = new MainTado(params)

        assert mainTado.save() != null

        params.id = mainTado.id

        def model = controller.show()

        assert model.mainTadoInstance == mainTado
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/mainTado/list'

        populateValidParams(params)
        def mainTado = new MainTado(params)

        assert mainTado.save() != null

        params.id = mainTado.id

        def model = controller.edit()

        assert model.mainTadoInstance == mainTado
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/mainTado/list'

        response.reset()

        populateValidParams(params)
        def mainTado = new MainTado(params)

        assert mainTado.save() != null

        // test invalid parameters in update
        params.id = mainTado.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/mainTado/edit"
        assert model.mainTadoInstance != null

        mainTado.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/mainTado/show/$mainTado.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        mainTado.clearErrors()

        populateValidParams(params)
        params.id = mainTado.id
        params.version = -1
        controller.update()

        assert view == "/mainTado/edit"
        assert model.mainTadoInstance != null
        assert model.mainTadoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/mainTado/list'

        response.reset()

        populateValidParams(params)
        def mainTado = new MainTado(params)

        assert mainTado.save() != null
        assert MainTado.count() == 1

        params.id = mainTado.id

        controller.delete()

        assert MainTado.count() == 0
        assert MainTado.get(mainTado.id) == null
        assert response.redirectedUrl == '/mainTado/list'
    }
}
