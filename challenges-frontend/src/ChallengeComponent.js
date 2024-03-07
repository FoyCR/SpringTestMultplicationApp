import * as React from "react";
import ApiClient  from "./ApiClient";

class ChallengeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            a: '',
            b: '', 
            user: '', 
            message: '',
            answer: 0 
        };
        this.handleSubmitAttempt = this.handleSubmitAttempt.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    //The componentDidMount function is a lifecycle method that you can implement
    // in React to execute logic right after the component is rendered the first time.
    componentDidMount(): void {
        ApiClient.challenge().then (
            res => {
                if (res.ok) {
                    res.json().then (
                    json => {
                        this.setState({
                            a: json.factorA,
                            b: json.factorB
                        });
                    });
                } else {
                    this.updateMessage("Can't reach the server");
                }                
            }
        );       
    }

    handleChange(event) {
        const name = event.target.name;
        this.setState({
            [name]: event.target.value
        });
    }

    handleSubmitAttempt (event) {
        event.preventDefault();
        ApiClient.sendAttempt(
            this.state.user,
            this.state.a,
            this.state.b,
            this.state.answer
        ).then(res => {
            if (res.ok) {
                res.json().then(json => {
                    if (json.correct) {
                        this.updateMessage("Congratulations! Your answer is correct!");
                    } else {
                        this.updateMessage("Sorry, Your answer is wrong, but keep playing!");
                    }

                });
            }else {
                this.updateMessage("Server Error or not available");
            }
        });
    }

    updateMessage(message: string) {
        this.setState({
            message: message
        });
    }

    render () {
        return (
            <div>
                <div>
                    <h2>Welcome to Foy's Mental Multiplication Challenge: </h2>
                    <h3>Your new challenge is: </h3>
                    <h1>{this.state.a} x {this.state.b}</h1>
                </div>
                <form onSubmit={this.handleSubmitAttempt}>
                    <label>
                        Your Alias: <input type = "text" maxLength="23" name="user" value={this.state.user} onChange={this.handleChange} />
                    </label>
                    <br/>
                    <label>
                        Your answer: <input type="text" name="answer" value={this.state.answer} onChange={this.handleChange}/>
                    </label>
                    <br />
                    <input type="submit" value="Send Attempt"></input>
                </form>
                <h4>{this.state.message}</h4>
            </div>
        );
    }
}

export default ChallengeComponent;