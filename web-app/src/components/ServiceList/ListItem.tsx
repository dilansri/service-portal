import React from 'react';
import { Service } from '../../types';
import {
  Link
} from 'react-router-dom';

interface Props {
  service: Service
};


const getBadgeColor = (code: Service['statusCode']): string => {

  if (!code) return 'gray'

  if (code >= 500) return 'red'

  if (code >= 400) return 'red'

  if (code >= 300) return 'yellow'

  if (code >= 200) return 'green'

  if (code >= 100) return 'blue'

  return 'gray'
};


export const ListItem: React.FunctionComponent<Props> = ({ service }) => {
  const statusColor = getBadgeColor(service.statusCode);
  
  return (
    <tr>
      <td className="px-6 py-4 whitespace-nowrap">
        <div>
          <Link to={`/view/${service.id}`}>
            <div className="text-sm font-medium text-gray-900">
              <span className="underline">{service.name}</span>
            </div>
            <div className="text-sm text-gray-500">
              {service.url}
            </div>
          </Link>
        </div>
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-900">{service.description || ''}</div>
        {/* <div className="text-sm text-gray-500">Optimization</div> */}
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <span className={`px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-${statusColor}-100 text-${statusColor}-800`}>
          {service.statusCode || 'unknown'}
        </span>
      </td>
      <td className="px-6 py-4 whitespace-nowrap">
        <div className="text-sm text-gray-500">{service.updatedAt}</div>
      </td>
      <td className="pl-6 py-4 whitespace-nowrap text-right text-sm font-medium">
        <button className="text-indigo-600 hover:text-indigo-900">Edit</button>
      </td>
      <td className="pr-6 py-4 whitespace-nowrap text-right text-sm font-medium">
        <Link to={`/delete/${service.id}`}>
          <button className="text-indigo-600 hover:text-indigo-900">Delete</button>
        </Link>
      </td>
    </tr>
  );
};
