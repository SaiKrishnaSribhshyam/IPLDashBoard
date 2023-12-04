import './App.css';
import { MatchPage } from './pages/Match';
import { TeamPage } from './pages/Team';
import { MatchSelectorPage } from './pages/MatchSelectorPage';
import {BrowserRouter as Router, Route,Routes} from 'react-router-dom';

function App() {
  return (
    <div className="App">
        <div className='titleBar'><h1>IPL DashBoard App</h1></div>
        <Router>
          <Routes>
            <Route path="/teams/:teamName/matches/:year" element={<MatchPage />} />
            <Route path="/teams/:teamName" element={<TeamPage />}/>
            <Route path='/matches' element={ <MatchSelectorPage/>} />
          </Routes>
        </Router>
    </div>
  );
}

export default App;
