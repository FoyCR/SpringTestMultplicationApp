class GameApiClient {
    static SERVER_URL = 'http://localhost:8081'; //base URL for the gamification endpoints
    static GET_LEADERBOARD = '/leaders'; //endpoint for GET the leader board
    static leaderBoard(): Promise<Response> {
        return fetch(`${GameApiClient.SERVER_URL}${GameApiClient.GET_LEADERBOARD}`); //fetching data from the REST API
    }
}
export default GameApiClient;