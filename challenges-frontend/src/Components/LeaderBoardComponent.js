import * as React from "react";
import GameApiClient from "../services/GameApiClient";
import ChallengeApiClient from "../services/ChallengeApiClient";

class LeaderBoardComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            leaderBoard: [],
            serverError: false
        }
    }

    componentDidMount() {
        this.refreshLeaderBoard();
        setInterval(this.refreshLeaderBoard.bind(this), 10000);
    }

    // Query the Game Api to get the leader board data
    getLeaderBoardData(): Promise {
        return GameApiClient.leaderBoard().then(
            res => {
                if (res.ok) {
                 return res.json();
                } else {
                    return Promise.reject("Gamification server not available");
                }
            }
        );
    }

    // Query the Challenge Api to get the user alias data by ids
    getUserAliasData(userIds: number[]): Promise {
        return ChallengeApiClient.getUsers(userIds).then(
            res => {
                if (res.ok) {
                    return res.json();
                } else {
                    return Promise.reject("Challenge server not available");
                }
            }
        );
    }

    // update the state of the component
    updateLeaderBoard(leaderBoardRows) {
        this.setState({
            leaderBoard: leaderBoardRows,
            serverError: false //reset the flag
        })
    }

    // get the leader board data and update the state triggered by an interval on componentDidMount function
    refreshLeaderBoard() {
        this.getLeaderBoardData().then(
          leaderBoardRows => {
                let userIds= leaderBoardRows.map(row => row.userId);
                this.getUserAliasData(userIds).then(data => {
                    //build a map of -> alias
                    let userMap = new Map();
                    data.forEach(idAlias => {
                        userMap.set(idAlias.id, idAlias.alias);
                    });
                    // add a property to existing leaderBoardRow
                    leaderBoardRows.forEach(row => row['alias'] = userMap.get(row.userId));
                    this.updateLeaderBoard(leaderBoardRows);
                }).catch(reason => {
                    console.log('Error mapping user ids to aliases', reason);
                    this.updateLeaderBoard(leaderBoardRows);
                });
            }
        ).catch(reason => {
            this.setState({
                serverError: true //set the flag in case of error to show a message in the render function
            });
            console.log('Error getting leaderboard data', reason);

        });
    }

    // render the leaderboard as a table
    render() {
        if (this.state.serverError) { //error case
            return (
                <div className="display-column">
                    <h2>Leaderboard is not available</h2>
                    <p>Please try again later</p>
                </div>
            );
        }
        return ( //data available
            <div>
                <h3>Leaderboard</h3>
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Score</th>
                            <th>Badges</th>
                        </tr>                    
                    </thead>
                    <tbody>
                        {this.state.leaderBoard.map(row =>
                        <tr key={row.userId}>
                            <td>{row.alias ? row.alias : row.userId}</td>
                            <td>{row.totalScore}</td>
                            <td>{row.badges.map(b => <span className="badge" key={b}>{b}</span>)}</td>
                        </tr>)}
                    </tbody>
                </table>
            </div>
        );
    }
}
export default LeaderBoardComponent;