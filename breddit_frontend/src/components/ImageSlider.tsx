import {useState} from 'react'
import {BaseModel} from "../models/models.ts";
import IconButton, {IconButtonType} from "./IconButton.tsx";
import {FaChevronLeft, FaChevronRight, FaCircle} from "react-icons/fa";
import {FaRegWindowMinimize} from "react-icons/fa6";

type CarouselProps = {
    images: BaseModel[],
    // autoSlide: boolean,
    // autoSlideInterval?: number,
}

export default function ImageSlider({images}: CarouselProps) {
    const [currentIndex, setCurrentIndex] = useState<number>(0);

    const previousSlide = () => {
        if (currentIndex === 0) setCurrentIndex(images.length - 1);
        else setCurrentIndex(currentIndex - 1);
    };

    const nextSlide = () => {
        if (currentIndex === images.length - 1) setCurrentIndex(0);
        else setCurrentIndex(currentIndex + 1);
    };

    const setSlide = (index: number) => {
        setCurrentIndex(index);
    }

    return (
        <section className="w-full h-full relative rounded-xl">
            <div className="w-full h-full flex overflow-hidden items-center align-middle rounded-xl">
                {images.map((url, index) => (
                    <img
                        key={index}
                        src={`data:image/jpeg;base64,${url}`}
                        alt="alt"
                        className="object-cover w-full h-full block shrink-0 grow-0 transition-all duration-500 ease-in-out rounded-xl"
                        style={{translate: `${-100 * currentIndex}%`}}
                        onClick={() => {
                            //TODO: check this?
                            fetch(`data:image/jpeg;base64,${url}`).then(res => res.blob()).then(blob => {
                                const seeImage = window.URL.createObjectURL(blob);
                                window.open(seeImage, "_blank");
                            })
                        }}
                    />
                ))}
            </div>

            {
                images.length > 1 && (<>
                    <div className="block absolute top-1/2 left-0 transform -translate-y-1/2 px-3">
                        <IconButton icon={<FaChevronLeft/>} onClick={previousSlide}/>
                    </div>

                    <div className="block absolute top-1/2 right-0 transform -translate-y-1/2 px-3">
                        <IconButton icon={<FaChevronRight/>} onClick={nextSlide}/>
                    </div>
                </>)
            }


            {   /*  Down indicator  */
                images.length > 1 &&
                <div className="absolute bottom-0 left-1/2 transform -translate-x-1/2 -translate-y-1/2 flex gap-2">
                    {
                        images.map((_, index) => (
                            index === currentIndex ?
                                <IconButton icon={<FaCircle
                                    className="text-primary-base hover:text-primary-lighter transition-all duration-200 ease-in-out hover:scale-125"/>}
                                            type={IconButtonType.EMPTY}
                                            onClick={() => setSlide(index)}
                                            key={index}
                                /> :
                                <IconButton icon={<FaRegWindowMinimize
                                    className="text-primary-base hover:text-primary-lighter transition-all duration-200 ease-in-out hover:scale-125 hover:-translate-y-0.5"/>}
                                            type={IconButtonType.EMPTY}
                                            onClick={() => setSlide(index)}
                                            key={index}
                                />
                        ))
                    }
                </div>
            }


        </section>
    );
}