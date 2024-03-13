import * as React from 'react'

class ListAttemptsComponent extends React.Component {
    render () {
        return (
            <table>
                <thead>
                    <tr>
                        <th>Challenge</th>
                        <th>Your answer</th>
                        <th>Correct</th>
                    </tr>
                </thead>
                <tbody>
                    {this.props.lastAttempts.map (a => 
                        <tr key={a.id} 
                            style={{color: a.correct? 'green': 'red'}}>
                                <td>{a.factorA} X {a.factorB}</td>
                                <td>{a.answer}</td>
                                <td>{a.correct ? "Correct": (`Incorrect (${a.factorA*a.factorB})`)}</td>
                            </tr>
                        )}
                </tbody>
            </table>
        );
    }
}
export default ListAttemptsComponent;