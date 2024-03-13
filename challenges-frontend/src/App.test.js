 import { render, screen } from '@testing-library/react';
import ChallengeComponent from './components/ChallengeComponent';


describe('App', () => {
  it('renders Challenge component', () => {
    render(<ChallengeComponent />);
  });

  
});
/* 
test('renders learn react link', () => {
  render(<App />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
}); */
 