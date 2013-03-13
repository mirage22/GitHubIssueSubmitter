package grails.tado.training

import org.springframework.dao.DataIntegrityViolationException

class IssueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)

//        [issueInstanceList: Issue.list(params), issueInstanceTotal: Issue.count()]

        def issues = Issue.list();

        println "List Issue " + issues.size()

        [issueInstanceList: issues, issueInstanceTotal: []]
    }

    def create() {
        [issueInstance: new Issue(params)]
    }

    def save() {
        def issueInstance = new Issue(params)
        if (!issueInstance.save(flush: true)) {
            render(view: "create", model: [issueInstance: issueInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'issue.label', default: 'Issue'), issueInstance.id])
        redirect(action: "show", id: issueInstance.id)
    }

    def show(Long id) {
        def issueInstance = Issue.get(id)
        if (!issueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "list")
            return
        }

        [issueInstance: issueInstance]
    }

    def edit(Long id) {
        def issueInstance = Issue.get(id)
        if (!issueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "list")
            return
        }

        [issueInstance: issueInstance]
    }

    def update(Long id, Long version) {
        def issueInstance = Issue.get(id)
        if (!issueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (issueInstance.version > version) {
                issueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'issue.label', default: 'Issue')] as Object[],
                        "Another user has updated this Issue while you were editing")
                render(view: "edit", model: [issueInstance: issueInstance])
                return
            }
        }

        issueInstance.properties = params

        if (!issueInstance.save(flush: true)) {
            render(view: "edit", model: [issueInstance: issueInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'issue.label', default: 'Issue'), issueInstance.id])
        redirect(action: "show", id: issueInstance.id)
    }

    def delete(Long id) {
        def issueInstance = Issue.get(id)
        if (!issueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "list")
            return
        }

        try {
            issueInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'issue.label', default: 'Issue'), id])
            redirect(action: "show", id: id)
        }
    }
}
