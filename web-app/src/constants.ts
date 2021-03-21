import { gql } from 'graphql-request';


/**
 * All Queries could live in a seperate place
 */
export const GET_SERVICES_QUERY = gql`
  query {
    services{
      id
      name
      url
      description
      statusCode
      updatedAt
    }
  }
`;

export const CREATE_SERVICE_QUERY = gql`
  mutation($service: ServiceInput!) {
    createService(service: $service){
      id
      name
      url
      description
      statusCode
      updatedAt
    }
  }
`;

export const GET_SERVICE_QUERY = gql`
  query($id: ID!) {
    service(id: $id){
      id
      name
      url
      description
      statusCode
      updatedAt
    }
  }
`;

export const DELETE_SERVICE_MUTATION = gql`
  mutation($id: ID!) {
    deleteService(id: $id)
  }
`;