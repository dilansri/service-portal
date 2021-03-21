import React, { useState, useEffect } from 'react';
import { ListComponent as ServiceListComponent } from './ListComponent';
import { useGraphQLClient } from '../../contexts';
import { Service } from '../../types';
import {GET_SERVICES_QUERY} from '../../constants'

const ServiceListContainer = () => {
  const client = useGraphQLClient();
  const [services, setServices] = useState<Service[]>([]);

  useEffect(() => {
    client.request(GET_SERVICES_QUERY)
    .then(({services} : {services: Service[]}) => setServices(services))
    .catch(() => { });
  }, [client]);

  return <ServiceListComponent services={services} />
};


export default ServiceListContainer;