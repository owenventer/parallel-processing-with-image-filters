import os

if __name__ == '__main__':
    window = 5
    os.system("make")

    count=1

    # test mean filter with different  sizes
    for imageNo in range(0, 5):
            print("Execution", count)
            #print("img no:"+str(imageNo))
            os.system(f"java "+"/Users/owen/OneDrive - University of Cape Town/My UCT/2nd Year/CSC2002S/Week 2/Assignment 1/bin/MeanFilterParallel.class"+" {imageNo}.jpg {imageNo}_W{window}.jpg {window}")
            count+=1

    # test median filter with different window sizes
    for imageNo in range(0, 5):
            print("Execution", count)
            #print("img no:"+str(imageNo))
            os.system(f"java "+"./../bin/MedianFilterParallel.class"+" {imageNo}.jpg MFP{imageNo}_W{window}.jpg {window}")
            count+=1

