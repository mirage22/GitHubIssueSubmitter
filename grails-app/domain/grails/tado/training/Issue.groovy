package grails.tado.training

class Issue {

    String title
    String content

    static constraints = {
        title(blank: false)
        content(blank: false)
    }
}
