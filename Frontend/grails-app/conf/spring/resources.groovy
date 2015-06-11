// Place your Spring DSL code here
beans = {

    //created a bean for making RESTful calls
    //bean is injected automatically

    restClient(grails.plugins.rest.client.RestBuilder)


}
