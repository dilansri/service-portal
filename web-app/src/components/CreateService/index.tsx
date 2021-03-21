import React, { useRef, useState } from 'react'
import { FormComponent } from './FormComponent'
import {
  useHistory
} from 'react-router-dom';
import { useGraphQLClient } from '../../contexts';
import { CREATE_SERVICE_QUERY } from '../../constants';

const CreateService = () => {

  //uncontrolled inputs
  const nameRef = useRef<HTMLInputElement>(null);
  const urlRef = useRef<HTMLInputElement>(null);
  const descriptionRef = useRef<HTMLTextAreaElement>(null);

  const [error, setError] = useState<string>();

  const history = useHistory();
  const client = useGraphQLClient();

  const onSave = () => {
    const name = nameRef?.current?.value
    const url = urlRef?.current?.value
    const description = descriptionRef?.current?.value || ''

    // simple field validations
    if (!name || !url) {
      setError('Empty fields');
      return;
    }

    client.request(CREATE_SERVICE_QUERY, { service: { name, url, description } })
      .then(() => {
        history.goBack();
      }).catch(() => {
        setError('Invalid values');
      });
  };

  return <FormComponent
    error={error}
    onSave={onSave}
    nameRef={nameRef}
    urlRef={urlRef}
    descriptionRef={descriptionRef}
  />
};

export default CreateService;