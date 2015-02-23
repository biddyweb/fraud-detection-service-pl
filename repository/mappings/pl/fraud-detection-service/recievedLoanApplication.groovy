io.coderate.accurest.dsl.GroovyDsl.make {
    request {
        method 'PUT'
        url '/api/loanApplication/256'
        headers {
            header 'Content-Type': 'application/json'
        }
        body '''\
{
    "firstName" : "Denis",
    "lastName" : "Stepanov",
    "job" : "Developer",
    "amount" : 100,
    "age" : 28
}
'''
    }
    response {
        status 200
    }
}