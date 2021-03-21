import React, { useContext } from 'react';
import { GraphQLClient } from 'graphql-request';

const ENDPOINT = 'http://localhost:8888/graphql';

type ContextValue = {
  client: GraphQLClient
};

const defaultGraphQLContext = {
  client: new GraphQLClient(ENDPOINT),
};

const GraphQLContext = React.createContext<ContextValue>(defaultGraphQLContext);

interface ProviderProps {
  children: React.ReactNode
  value?: ContextValue,
};


export const GraphQLContextProvider = ({ children, value }: ProviderProps) => {
  return <GraphQLContext.Provider value={value || defaultGraphQLContext}>{children}</GraphQLContext.Provider>
};

export const useGraphQLClient = () => {
  const { client } = useContext(GraphQLContext)

  return client;
};
