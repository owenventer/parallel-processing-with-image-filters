import os

if __name__ == '__main__':
    window = 5
    os.system("make")
    ourF="MeanFilterSerial cat.jpg OutputMeanSCat.jpg 9"

    count=1

    # test mean filter with different  sizes
    for imageNo in range(0, 5):
            print("Execution", count)
            #print("img no:"+str(imageNo))

            os.system(f"java -cp {ourF}")
            count+=1

    # test median filter with different window sizes
    for imageNo in range(0, 5):
            print("Execution", count)
            #print("img no:"+str(imageNo))
            os.system(f"java -cp MedianFilterParallel {imageNo}.jpg MFP{imageNo}_W{window}.jpg {window}")
            count+=1

