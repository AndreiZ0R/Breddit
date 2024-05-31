import {ChangeEvent, DragEvent, useRef, useState} from "react";
import {MdOutlineFileUpload} from "react-icons/md";
import {FaAnglesDown} from "react-icons/fa6";

type FileUploaderProps = {
    onImageUploaded: (image: File) => void,
}

export default function FileUploader({onImageUploaded}: FileUploaderProps) {

    const [dragActive, setDragActive] = useState<boolean>(false)
    const inputRef = useRef<HTMLInputElement | null>(null);

    const upload = () => {
        inputRef.current?.click();
    };


    const onDrop = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        if (e.dataTransfer?.files && e.dataTransfer.files[0]) {
            onImageUploaded(e.dataTransfer.files[0]);
        }
        setDragActive(false);
    };
    const onDragOver = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setDragActive(true);
    };
    const onDragLeave = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setDragActive(false);
    };
    const onDragEnter = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setDragActive(true);
    };
    const onChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            onImageUploaded(event.target.files[0]);
        }
    };
    return (
        <>
            <input type="file" multiple={false} className="hidden" ref={inputRef} onChange={onChange} accept=".jpg,.png,.jpeg"/>
            <div className={`transition-all duration-200 w-full min-h-60 border-2 border-dotted
                    ${dragActive ? "border-primary-lighter bg-primary-base/5" : "border-primary-base"} rounded-md flex flex-col items-center justify-center 
                    text-primary-base text-center 
                    `}
                 onDragEnter={onDragEnter}
                 onDragLeave={onDragLeave}
                 onDragOver={onDragOver}
                 onDrop={onDrop}
            >
                {dragActive ?
                    <FaAnglesDown size={55} className="text-primary-lighter"/> :
                    <MdOutlineFileUpload size={55}/>
                }
                <span>Drop your profile picture here or</span>
                <div onClick={upload}
                     className={`underline hover:text-primary-lighter text-primary-base transition-all duration-200 cursor-pointer`}>browse
                </div>
            </div>
        </>

    )
}