import React, { MutableRefObject } from 'react'
import { Container } from '../../ui';

interface Props {
  error?: string
  nameRef: MutableRefObject<HTMLInputElement | null>
  urlRef: MutableRefObject<HTMLInputElement | null>
  descriptionRef: MutableRefObject<HTMLTextAreaElement | null>
  onSave: () => void
}

export const FormComponent: React.FunctionComponent<Props> = ({ nameRef, urlRef, descriptionRef, onSave, error }) => {

  return (
    <Container>
      {error && <div className="flex justify-center items-center m-1 font-medium py-1 px-2 bg-white rounded-md text-red-700 bg-red-100 border border-red-300 ">
        <div className="text-xl font-normal  max-w-full flex-initial">
          {error}</div>
      </div>}
      <div className="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg">

        <div className="shadow sm:rounded-md sm:overflow-hidden">
          <div className="px-4 py-5 bg-white space-y-6 sm:p-6">
            <div className="col-span-6 sm:col-span-4">
              <label htmlFor="service_name" className="block text-sm font-medium text-gray-700">Service name</label>
              <input required ref={nameRef} type="text" name="service_name" id="service_name" placeholder="Enter the name of your" className="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" />
            </div>
            <div className="col-span-6 sm:col-span-4">
              <label htmlFor="service_url" className="block text-sm font-medium text-gray-700">Service uri</label>
              <input required ref={urlRef} type="text" name="service_url" id="service_url" placeholder="http://your-service.com/ping" className="mt-1 focus:ring-indigo-500 focus:border-indigo-500 block w-full shadow-sm sm:text-sm border-gray-300 rounded-md" />
            </div>

            <div>
              <label htmlFor="service_description" className="block text-sm font-medium text-gray-700">
                Service description
              </label>
              <div className="mt-1">
                <textarea ref={descriptionRef} id="service_description" name="service_description" placeholder="Describe about your service here" rows={3} className="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 mt-1 block w-full sm:text-sm border-gray-300 rounded-md"></textarea>
              </div>
            </div>
          </div>
          <div className="px-4 py-3 bg-gray-50 text-right sm:px-6">
            <button onClick={onSave} className="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
              Save
            </button>
          </div>
        </div>
      </div>
    </Container>
  )
}