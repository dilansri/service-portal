import React from 'react';
import { Header, Container } from '../../ui';
import { Service } from '../../types';

// local Row component since it only needed for the exported ViewComponent for now.
const Row = ({ name, value, index }: { name: string, value?: string | number, index: number }) => {
  const bgColor = index % 2 === 0 ? 'bg-gray-50' : 'bg-white';

  return (
    <div className={`${bgColor} px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6`}>
      <dt className="text-sm font-medium text-gray-500">
        {name}
      </dt>
      <dd className="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
        {value}
      </dd>
    </div>
  )
}

export const ViewComponent = ({ service }: { service?: Service }) => {

  if (!service) return null

  return (
    <>
      <Header title={`${service.name} - Info`} />
      <Container>
        <div className="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">
          <div className="bg-white shadow overflow-hidden sm:rounded-lg">

            <div className="border-t border-gray-200">
              <dl>
                {
                  Object.entries(service)
                    .map(
                      ([name, value], index) => <Row name={name} value={value} index={index} />
                    )
                }
              </dl>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
};