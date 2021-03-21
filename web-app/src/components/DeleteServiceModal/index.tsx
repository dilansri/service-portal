import React, { useState, useEffect } from 'react';
import { ModalComponent } from './ModalComponent';
import {
  useParams,
  useHistory
} from 'react-router-dom';
import { useGraphQLClient } from '../../contexts';
import { Service } from '../../types';
import { DELETE_SERVICE_MUTATION, GET_SERVICE_QUERY } from '../../constants';

const DeleteServiceModal = () => {
  const [service, setService] = useState<Service | undefined>();

  const { id } = useParams<{ id: string }>();
  const history = useHistory();
  const client = useGraphQLClient();

  const onCancel = () => {
    history.goBack();
  };

  const onDelete = () => {
    client.request(DELETE_SERVICE_MUTATION, { id })
      .then(() => {
        history.goBack();
      })
      // failing silently. Should be properly handled
      .catch(() => { })
  }

  useEffect(() => {
    // make a request during the opening of the model to retrieive the latest service data
    client.request(GET_SERVICE_QUERY, { id })
      .then(({ service }: { service: Service }) => setService(service))
      // failing silently. Should be properly handled
      .catch(() => { });
  }, [client, id]);

  return <ModalComponent
    service={service}
    onCancel={onCancel}
    onDelete={onDelete}
  />;
}

export default DeleteServiceModal;