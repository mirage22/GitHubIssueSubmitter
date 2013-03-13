import grails.tado.training.Issue

class BootStrap {

    def init = { servletContext ->

        new Issue(title: "ussee1", content: "Content1").save(failOnError: true)
        new Issue(title: "ussee2", content: "Content2").save(failOnError: true)
        new Issue(title: "ussee3", content: "Content3").save(failOnError: true)
        new Issue(title: "ussee4", content: "Content4").save(failOnError: true)
        new Issue(title: "ussee5", content: "Content5").save(failOnError: true)
        new Issue(title: "ussee6", content: "Content6").save(failOnError: true)
    }
    def destroy = {
    }
}
