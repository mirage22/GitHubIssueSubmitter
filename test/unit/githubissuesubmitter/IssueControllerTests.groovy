package githubissuesubmitter



import org.junit.*
import grails.test.mixin.*

@TestFor(IssueController)
@Mock(Issue)
class IssueControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/issue/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.issueInstanceList.size() == 0
        assert model.issueInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.issueInstance != null
    }

    void testSave() {
        controller.save()

        assert model.issueInstance != null
        assert view == '/issue/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/issue/show/1'
        assert controller.flash.message != null
        assert Issue.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/issue/list'

        populateValidParams(params)
        def issue = new Issue(params)

        assert issue.save() != null

        params.id = issue.id

        def model = controller.show()

        assert model.issueInstance == issue
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/issue/list'

        populateValidParams(params)
        def issue = new Issue(params)

        assert issue.save() != null

        params.id = issue.id

        def model = controller.edit()

        assert model.issueInstance == issue
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/issue/list'

        response.reset()

        populateValidParams(params)
        def issue = new Issue(params)

        assert issue.save() != null

        // test invalid parameters in update
        params.id = issue.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/issue/edit"
        assert model.issueInstance != null

        issue.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/issue/show/$issue.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        issue.clearErrors()

        populateValidParams(params)
        params.id = issue.id
        params.version = -1
        controller.update()

        assert view == "/issue/edit"
        assert model.issueInstance != null
        assert model.issueInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/issue/list'

        response.reset()

        populateValidParams(params)
        def issue = new Issue(params)

        assert issue.save() != null
        assert Issue.count() == 1

        params.id = issue.id

        controller.delete()

        assert Issue.count() == 0
        assert Issue.get(issue.id) == null
        assert response.redirectedUrl == '/issue/list'
    }
}
