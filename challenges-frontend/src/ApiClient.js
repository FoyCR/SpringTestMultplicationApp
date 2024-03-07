class ApiClient {
    static SERVER_URL = 'http://localhost:8080'; //base URL for the endpoints
    static GET_CHALLENGE = '/challenges/random'; //endpoint for GET randomly generated challenge
    static POST_ATTEMPT = '/attempts'; //endpoint for POST the attempt to solve a challenge

    static challenge(): Promise<Response> {
        return fetch(`${ApiClient.SERVER_URL}${ApiClient.GET_CHALLENGE}`); //fetching data from the REST API
    }

    static sendAttempt(user: string, a: number, b: number, answer: number): Promise<Response> {
        return fetch(`${ApiClient.SERVER_URL}${ApiClient.POST_ATTEMPT}`, //Posting the attempt to tne endpoint
        {
            method:'POST',
            headers: {'Content-Type':'application/josn'},
            body: JSON.stringify(
                {
                    userAlias: user,
                    factorA: a,
                    factorB: b,
                    answer: answer
                }
            )
        });
    }
}
export default ApiClient;