import React from 'react';

interface Props {
  children: React.ReactNode
};

export const Container: React.FunctionComponent<Props> = ({ children }) => {
  return (
    <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 flex flex-col">
      <div className="-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div className="py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8">
          {children}
        </div>
      </div>
    </div>
  );
};