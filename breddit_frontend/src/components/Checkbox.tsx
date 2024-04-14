import {useState} from "react";

export default function Checkbox() {
    //TODO: edit logic for parent here
    const [isChecked, setIsChecked] = useState(false)

    const handleCheckboxChange = () => {
        setIsChecked(!isChecked)
    }
    return (
        <>
            {/*<div className="flex items-start mb-6">*/}
            {/*    <div className="flex items-center h-5">*/}
            {/*        <input id="remember" type="checkbox" value=""*/}
            {/*               className="w-4 h-4 border border-secondary-base rounded bg-red focus:ring-3 focus:ring-primary-base accent-primary-base"*/}
            {/*               required*/}
            {/*        />*/}
            {/*    </div>*/}
            {/*    <label htmlFor="remember" className="ms-2 text-sm font-medium text-gray-400 ">I agree with the aaa.</label>*/}
            {/*</div>*/}
            <label className='autoSaverSwitch relative inline-flex cursor-pointer select-none items-center'>
                <input
                    type='checkbox'
                    name='autoSaver'
                    className='sr-only'
                    checked={isChecked}
                    onChange={handleCheckboxChange}
                />
                <span className={`slider mr-3 flex h-[26px] w-[50px] items-center rounded-full p-1 duration-200 ${
                        isChecked ? 'bg-primary-base' : 'bg-background-hover'
                    }`}
                >
          <span
              className={`dot h-[18px] w-[18px] rounded-full bg-white duration-200 ${
                  isChecked ? 'translate-x-6' : ''
              }`}
          ></span>
        </span>
                <span className='label flex items-center text-sm font-medium text-background-text'>
          Auto Saver <span className='pl-1'> {isChecked ? 'On' : 'Off'} </span>
        </span>
            </label>
        </>
    );
}