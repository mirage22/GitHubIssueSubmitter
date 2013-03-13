package grails.tado.training

import org.springframework.dao.DataIntegrityViolationException

class MainTadoController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {



//        redirect(action: "list", params: params)
    }


}
