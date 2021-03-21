import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom';
import ServiceList from '../ServiceList';
import ServiceView from '../ServiceView';
import DeleteServiceModal from '../DeleteServiceModal';
import CreateService from '../CreateService';
import { TopBar, Header } from '../../ui';

const Main : React.FunctionComponent = () => {
  return (
    <Router>
      <Link to="/">
        <TopBar />
      </Link>
      <Switch>
          <Route path="/create">
            <CreateService />
          </Route>
          <Route path="/view/:id">
            <ServiceView />
          </Route>
          <Route path="/delete/:id">
            <DeleteServiceModal />
          </Route>
          <Route exact path="/">
            <Header title="Services"/>
            <ServiceList />
          </Route>
        </Switch>
    </Router>
  );
};

export default Main;
