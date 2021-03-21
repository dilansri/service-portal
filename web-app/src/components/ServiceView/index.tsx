import React, { useState, useEffect } from 'react'
import { ViewComponent } from './ViewComponent'
import {
  useParams
} from 'react-router-dom'
import { useGraphQLClient } from '../../contexts';
import { Service } from '../../types';
import { GET_SERVICE_QUERY } from '../../constants';


const ServiceView: React.FunctionComponent<{}> = () => {

  const [service, setService] = useState<Service | undefined>()

  const { id } = useParams<{ id: string }>();
  const client = useGraphQLClient();

  useEffect(() => {
    client.request(GET_SERVICE_QUERY, { id })
      .then(({ service }: { service: Service }) => setService(service))
      .catch(() => { });
  }, [client, id])

  return <ViewComponent service={service} />
};

export default ServiceView;