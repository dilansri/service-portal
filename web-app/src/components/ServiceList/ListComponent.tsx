import React from 'react';
import {
  Link
} from 'react-router-dom';
import { Service } from '../../types';
import { ListHeader } from './ListHeader';
import { ListItem } from './ListItem';
import { Container } from '../../ui';

interface Props {
  services: Service[]
};

export const ListComponent: React.FunctionComponent<Props> = ({ services }) => {
  return (
    <Container>
      <Link to="/create">
        <button className="mb-10 inline-flex justify-right py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
          Add new
            </button>
      </Link>
      <div className="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">
        <table className="min-w-full divide-y divide-gray-200">

          <ListHeader />

          <tbody className="bg-white divide-y divide-gray-200">

            {services.map(service => {
              return <ListItem key={service.id} service={service} />;
            })}

          </tbody>
        </table>

      </div>

    </Container>
  );
};

