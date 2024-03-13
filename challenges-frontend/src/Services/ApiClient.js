class ApiClient {
    static SERVER_URL = 'http://localhost:8080'; //base URL for the endpoints
    static GET_CHALLENGE = '/challenges/random'; //endpoint for GET randomly generated challenge
    static POST_ATTEMPT = '/attempts'; //endpoint for POST the attempt to solve a challenge
    static GET_ATTEMPTS_BY_ALIAS ='/attempts?alias='; // endpoint for GET the attempts for user's alias

    static challenge(): Promise<Response> {
        return fetch(`${ApiClient.SERVER_URL}${ApiClient.GET_CHALLENGE}`); //fetching data from the REST API
    }

    static sendAttempt(user: string, a: number, b: number, answer: number): Promise<Response> {
        return fetch(`${ApiClient.SERVER_URL}${ApiClient.POST_ATTEMPT}`, //Posting the attempt to tne endpoint
        {
            method:'POST',
            headers: {'Content-Type':'application/json'},
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

    static getAttempts(userAlias: string): Promise<Response> {
        return fetch(`${ApiClient.SERVER_URL}${ApiClient.GET_ATTEMPTS_BY_ALIAS}${userAlias}`);
    }
}
export default ApiClient;