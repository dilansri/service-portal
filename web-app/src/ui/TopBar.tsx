import React from 'react';

export const TopBar : React.FunctionComponent = () => {
  return (
    <div className="bg-gray-800">
      <div className="max-w-7xl mx-auto py-6 px-4 sm:px-6 lg:px-8 ">
        <div className="flex items-center justify-between h-16"></div>
        <div className="flex items-center">
          <div className="sm:block">
            <div className="flex items-baseline space-x-4">
              <h1 className="text-gray-300 py-5 rounded-md text-5xl font-medium">
                Service Portal
                </h1>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
};