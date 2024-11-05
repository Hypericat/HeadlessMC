# Minecraft Headless Client

 
## Introduction 

The aim of this project is to create a program capable of emulating a Minecraft client, using Java. The Headless Client is written in pure Java without any connection to the original client. The Headless Client can join, interact and move around using pathfinding. The user can interact with the client using a terminal window. The advantage of Headless Client is that the resource cost of running an instance is heavily diminished. This allows for multithreading of instances and creating tens to hundreds of instances all connecting to the same server from a single computer. The Headless Clients could be extremely useful in automating long and boring tasks with hundreds of asynchronous accounts. Furthermore, these bots can also be used to lag or crash servers easily, and logging data on many different servers. 

 

## Pre-Requisites 

This project is by no means simple, it requires an understanding of Java, the Transmission Control Protocol (TCP), compression, the internet protocol (IP), variable length variables and pathfinding algorithms. Although these are heavily used and really require a deep understanding in order to be implemented, a surface level explanation will be provided. 

### Java 

The main importance of this section is to go over data structures and their respective sizes as they are used as a basis for understanding other pre-requisites. Every primitive number data structure in Java is unsigned. This means that the first bit is always reserved for signedness (negative or positive). 0 represents positive values while 1 represents negative values. The max capacity of for an unsigned number data structure can be calculated using 2 ^ n where n is the size in bits. In the case of a signed number the maximum positive value can be calculated using 2 ^ (n – 1) - 1 as we have one less bit to represent the signedness. Bytes are the simplest of data structures, they are 8 bits long and can store values from -128 to 127. Shorts, Integers and Longs are similar to bits but with respective sizes of 16, 32 and 64 bits. Chars are special in java. They usually represent ASCII characters in languages such as C++ and therefore have 8 unsigned bits. In Java they have a size of 16 bits as they can instead represent the entire UTF – 8 standards. They are the only exception as they are unsigned rather than signed like all other data structures. Floats and Doubles are both signed data structures having 32 and 64 bits respectively. These are floating point numbers which means that they can represent small decimal numbers as well as huge numbers with Doubles capable of counting to 308 digits. Floating point numbers are an implementation of the IEEE 754 standard, which basically describes the mathematical expression used for storing these values in only 32 and 64 bits. The float is primarily used as the common implementation. The value of a float is calculated similarly to scientific notation (ex: 3 * 10 ^ -3) however using base 2 for binary instead of 10.  The 32 bits of a float are split up into three sections. The first bit represents the sign. Followed by 8 exponent bits and finally 23 bits of the mantissa. To find the value of the float, first the exponent is calculated. The exponent is an 8-bit unsigned byte. The value of it is equal to 2 ^ (x – 127) where x is the value of the exponent. The -127 allows us to have negative exponent values. The mantissa is an unsigned 23-bit data structure. The value of the mantissa is calculated using x / 2 ^ n + 1 where x is the literal value of the mantissa as an unsigned number and n is the size in bits of the mantissa (23 for floats). The total value of the float is calculated by multiplying the sign, exponent and mantissa together. Floating point numbers can adjust their precision between high and low values simply by having a smaller exponent (which becomes negative) for small values and large exponent for big ones. Floating point numbers trade in their precision for a more diverse set of values. This project makes heavier use of Doubles because of their better precision and the same logic applies to their implementation except with more bits. 

 

### The Internet Protocol 

At the moment Minecraft still relies on the old internet protocol IPv4 rather than its newer more available brother IPv6. An IPv4 address consists of 4 unsigned 8-bit bytes. Each byte can range from 0 to 255 for a total of 2 ^ 32 combinations. An IPv4 address is accompanied by an unsigned 16-bit port which represents different outlets an incoming connection can connect to. The internet protocol is not reliable and often drops packets or sends them out of order, it therefore can’t be used to communicate important information. 

 

 

 

### TCP 

TCP or the transmission control protocol is a layer of abstraction based around IP packets that promise to send and receive all packets and in the exact order they were sent. It does this by adding a TCP header with extra information and expecting TCP ACK or acknowledgment packets in return. This wrapper around inconsistent IP packets makes the connection slower but at least reliable. TCP also has the benefit of not having to worry about the order of packets. 

 

### Compression 

There are two main types of compression, lossless and lossy compression. Lossy compression is used on mainly videos and images where extra data is thrown out if the original image looks about the same. For this project, we care about lossless compression, in which extra bytes and data are merged and represented into a smaller form. ZIPs are a common example of lossless compression, where similar patterns are joined together to save space. This project uses the library ZLib which is a wrapper around the deflate algorithm, therefore the specifics of the deflation algorithm don’t matter because smarter people have already done this for us. 

### Pathfinding 

The goal of a pathfinder is to find the shortest path from point A to B. In a 3D environment this is expensive as the amount of positions to search grows exponentially. The most obvious technique to pathfinding is to start at point A and perform a flood fill (i.e. Checking every block one in a radius around you and increasing the radius every time) until you reach point B. This is extremely slow and un optimal, hence better algorithms such as Dijkstra’s exist. Dijkstra’s algorithm is similar to a flood fill algorithm however it prioritizes searching the shortest paths. Once Dijkstra completes, it will contain the shortest path to every existing node from the start position. This is better but still not good enough, the main problem is that Dijkstra has no sense of directionality, if for example our goal was westwards, and there was a shorter path eastwards Dijkstra would prioritize expanding eastwards nodes in the total opposite direction. A* is an extension of Dijkstra, it prioritizes finding the shortest path to B instead of to every other node. If n is the current node, let f(n) be the cost or viability of a given node. In A* f(n) is equal to g(n) + h(n), g is the current best cost of this node, the cost is the sum of the costs of all previous nodes starting at A to this node. This is the base for Dijkstra’s algorithm, however A* adds on a heuristic, defined as h(n). The heuristic is an estimation of the remaining distance from the current node to B. A* uses a queue system, the algorithm will explore nodes on top of the queue. This will mean that nodes further away will have a greater heuristic cost and therefore have and therefore a greater cost encouraging pathing towards B and not away from it. A* is only admissible meaning it returns the shortest path if the heuristic to B is itself admissible. In practice, the heuristic often is calculated using the Pythagorean distance to B from the current node. This means that it is often not the optimal result and therefore A* would be inadmissible and return a suboptimal solution, although a valid solution, nonetheless. This means that the better the heuristic the lower the time complexity and the faster the result. In a 3D environment we use a combination of Manhattan distance of Pythagorean theorem to get a more accurate heuristic. 

### Variable length variables 

VarInts and VarLongs are variable length numbers that encode its length within itself. This is often useful when dealing with small values as we don’t need to send an entire 8 bytes for a 1-byte value. VarInts are most commonly used and will therefore only explain with VarInts. However, VarLongs work similarly with different amounts of bits. To transform an int into a VarInt the idea is to make bytes of only 7 rather than 8 and use the first bit to specify whether it is a continue bit (1) or end (segment) bit (0). The continued bits are 10000000 while the segment bits are 01111111. We can set these bits by using AND on the int and the segment bits. This isolates the first 7 bits. Then if it is a continue byte, we can OR it with the continue bits to set the first bit to 1. If it is instead the end byte, we can simply write the value as the first bit should be a 0 anyways. After doing this we bit shift the int 7 bits and do this again until there are no more bits left. 

## Networking 

The first step to connecting to a headless client is to be able to communicate with the server. The Minecraft protocol uses TCP over port 25565. Packets always follow a certain format. The length field is self-explanatory, the Packet ID is an integer given to each packet so that the server/client knows what packet is being sent / received. The data contains the actual packet in an array of bytes. The major thing I wanted to implement in the networking system was easy abstraction. I did not want to have to manually send the bytes one at a time and figure out what the numbers meant. I wanted to just be able to tell the client that now it should send this connection to packet. I created the packet interface which every packet implements, this makes sure that each packet type must define an encode and decode method which converts it into a byte array to be sent. All I must do is write the to encode and decode methods. For the networking library I used Netty, this is the same library that Minecraft uses. Each incoming packet gets handled by a new thread and gets identified by its packet id. The client also has 4 different internal states: Handshake, Login, Configuration and Play. Packets also have 2 different directions: Clientbound and Serverbound. Each state and direction has its own set of Packet ID, an ID of 0 doesn’t mean the same packet depending on when (state) and where (direction) it is received. TCP can chop a big packet into other smaller packets which means that if the packet size is longer than the packet we received, we need to ignore this packet and store it for now. When we receive the next packet, we append the first to the beginning. This makes sure that all packets are complete. A long list of every packet name and its respective Packet ID is used to identify the packets which allows us to translate IDs into human readable names. Here is a diagram of the 


 
## Chunk and Block Logic 

Chunk data is sent along using the “Chunk Data and Update Lighting” packet. This leviathan of a packet is massively overcomplicated, from all the fields the only we really care about is Chunk X / Y, the chunk size and data. A Minecraft chunk is a 16 by 16 block section, it goes all the way from the lowest to highest position in the world. We need to keep in mind that the packet is received as a byte array. We don’t know when and where each section starts, so the only way to know where a certain field is (and by extension retrieve the field) is to read all the previous fields. This means that to read the data field we care about, we also need to read the useless heightmap. The heightmap is stored in a proprietary NBT format which we will need to read and understand, for the moment here is a link to the NBT protocol, just know that we are skipping over it for the moment. The hardest part of the chunk is interpreting the data, the bytes of data are Chunk Section objects, Chunks Sections are sections of a chunk 16 blocks high. Here is the format for a chunk section. The Block States contains the actual block information, first we have a short representing the amount of blocks in the chunk section. The block information is stored in the Block state paletted container, but as said above if we want to read further information in a byte array, we need to process all the information before it, so this means we also need to read the Biome data which we frankly don’t care about at all.  Here is the structure for a paletted container. Since there are a lot of blocks in a chunk the blocks are stored as bits, the bits represent the Block State (which is just a number) of the block. Since we can’t store bits directly, the bits are stored in longs which means we need to use bit shifts and bit operations to extract the bits from the long. The blocks are ordered, by their block coordinates, first the X, then Z and Y. Imagine taking the 16 * 16 * 16 section of blocks and reordering them into a line, this is what we need to undo in order to get their actual positions. The Bits Per Entry field is the count of how many bits are allocated for each block, the length is the same for every block within the same section. The Palette complicates things heavily, but we will come back to these later. The data array length species the amount of longs inside the data array. Longs contain the most bits and therefore can store the most blocks. There are three types of palettes simple, direct and indirect. Simple is the easiest, basically it provides a single block state ID, and the data array is empty. The client infers that every block in that section is of the type of that ID. In practice this is only really used for empty chunks, with only air in them. With direct we store all the state IDs into the data array directly, this is quite expensive as we end up duplicating IDs so this is only used for chunk sections with 100 + different blocks. Indirect is the most common, basically the palette contains a list of every block state ID that will be used. The block data then refers to an index into that array of IDs. This is much less expensive as the array might have indices ranging from 0 to 30 while the state IDs have values going into the tens of thousands. It takes way less bits to store a small value like 32 than other huge numbers especially when storing 4000 blocks. Once we’ve read every chunk section, we can throw away the rest of the packet as we don’t really care anymore we have the data that we need. The hardest part is reading back the block. I added comments explaining what everything does. The method used to read blocks from memory. We use this method to extract only certain bits from a long. First, we use an unsigned right shift, this moves the bits right bitIndexStart amount and creates 0s to the left when we shift. Then we take a long of 1 and bit shift it by the length and then subtract 1. This is like setting a number to 1 then adding length amount of zeros (ex: 10,000). Then we subtract 1 giving 9999. However, this happens in base 2 so we get 01111. The subtraction (-) operand takes precedence over the bitwise AND (&) operand, only at the end do we AND the bit shifted long with our length (which would now look like 000000001111). The AND operator only 1 if both bits at that position are one, if not it outputs 0. This means that we only read the bits that we want from the Long. We then return that value as it should already be in the correct position. We also need to implement the Update Block and Update Block Section packets. These packets are sent to the client while the player already has a chunk loaded. Sending the whole chunk again would be a massive waste of resources, hence the server only sends the changed blocks. The Update Block Section is used when multiple blocks within the same chunk section are changed within the same tick. These packets are straightforward, we only need to read the position(s) and the changed block(s). Then we set the block at the appropriate chunk section. We already have code to get the bits from a XYZ from code above, instead of getting the block we just set it. 

 

 

 

## Client Logic 

From the main loop, each client instance (because the program can run multiple clients from the same program instance) gets its own thread, and each of these instances runs its own main loop. A Minecraft tick occurs every 50ms (20 ticks per second), a tick is like a frame but for internal physics and calculations as opposed to rendering. The Minecraft client is responsible for handling positions, the client sends a PlayerMoveC2SPacket with it’s new positions. The server performs checks to make sure the delta between the old position and the new position isn’t too high. Every tick we send a PlayerMove packet with our internal position. The client has a velocity for X, Y and Z, before sending the movement, the velocity is added to the current position and the velocity is multiplied by 0.98 to simulate drag (this is the equation used in the base game). Additionally, if the player is floating, we add a downwards velocity to simulate gravity.  

 

 

## Block Mining 
Now that the client processes blocks and is aware of its surroundings, we can mine blocks. The player can mine blocks using the Player Actions C2S packet. Status should be the state of our mining operation (Started digging or Finished digging). When we start mining a block we obviously need to send started digging. When we think, we are finished mining the block (it should break) we need to send Finished digging, however we don’t really have a way of calculating mining time, because that would require reading items which we can’t do yet. With testing, waiting for 4 ticks between the start and finish seems to work. Even if the block break would take longer than 4 ticks, the server breaks it anyways when it would be planned to break normally. The only downside is that if the block takes 3 ticks or less, we still can only start mining other blocks after 4 ticks. Every block takes at least one tick to dig, therefore our maximum loss is (3 * 50ms) 150ms which is acceptable for now. 

 

## Entity Positions 
Entities are sent to the player using the spawnEntityS2CPacket, the fields are listed in the diagram below. The UUID is composed of 64-bit longs, all entities have them, but they are mainly used for players. The Entity ID is a 32-bit integer, this ID is different every time you reload the entity. This means that entities can have the same ID at different times. The UUID is unique, 2 entities will never have the same UUID. The type of entity is used along with a registry (a long list of all types, will explain this further later) of entity types to get the type. The World class which contains all the chunks and blocks also contains a Hashmap of every loaded entity. A Hashmap allows us to hash a value to get an index and then put that value at that index. In this case we use the entity ID and an entity object which we create. A Hashmap is useful because its worst case performance is O(1). This means that no matter how many elements we add to it, the length to retrieve an element will always be the same. If we were to store the entities into a list instead, the worst-case performance would be O(n). This is because to find an element we need to iterate through the entire list to find it. The data field isn’t important for now. The positions of the entities are sent in the UpdateEntityPosition packet. The packet specifies the entity ID along with a delta XYZ. We add the delta to the current position to get the new position. The TeleportEntityPacket	is used when the delta between the last and new position is greater than 8. This packet is similar to the previous, however it specifies absolute position not a delta. We then get that entity by the ID and update its position. 

 

## Packet Compression 
Packet compression is enabled by default on all Minecraft servers. Before this point, I had manually disable packet compression to be able to join the server. Packet compression changes the format and headers which are sent before sending the data. We first need to determine whether the packet is over the threshold (more on this later). The threshold is by default 256 bytes, therefore if the deflated packet is smaller than 256 bytes, we don’t bother to send the compressed version we send the raw packet data. If we are sending the raw packet data, the Data Length field should be set to 0 to indicate an uncompressed packet. In case of a compressed packet, we deflate the byte array containing the packet Data. The Data Length field should be uncompressed and represent the total length of the uncompressed packet so it can be reassembled. Finally, we need to specify an uncompressed packet length, this is the total length in bytes of either the compresses or uncompressed packet plus the length of Data Length in bytes. This should create a compresses packet that the server will accept. Packet Compression is enabled by the server after sending the SetCompressionS2CPacket, this packet only contains one VarInt which is the aforementioned threshold. With packet compression enabled, we not only need to be able to send packets but also to read them, which is the difficult part. We first need to read the packet length and make sure that we have the entire packet, if not we need to return and append this to the start of the next packet. We then read the data length to determine whether the packet length is compressed or uncompressed. We then subtract the length in bytes of the data length from the packet length to get the size of the uncompressed or raw data. We can then inflate (decompress) if needed and appended to a new array, which completes the decompression. 

 

 

 

 

## Collision 

The headless client needs to implement some form of collision, this is because if the client sends an invalid position too far away or inside a block, the server will refuse the Move packet and reset the client position. Therefore, if we want to move, the client must be able to detect whether a certain position is valid and prevent itself from falling through the floor causing itself to be unable to move. Minecraft collision is handled in an orthodox way. Usually, collision is handled via a raycast from the player bounding box to the newly calculated position based on the velocity. The raycast is then stepped backwards until a valid inbounds position is found. Instead, Minecraft computes each direction individually and steps back the required amount for the position to be inbounds. It first computes the Y and then either X or Z depending on which has the greatest value in the velocity vector. When we compute collision for a direction, we first calculate the blocks at the area (basically every block that our bounding box intersects with) and extend it by the current direction blocks.  

 

## Terminal Commands 

Before we create the instances, we create a terminal instance on a different thread. We then pass the client instances to the terminal which allows the terminal to control all the instances at the same time. The terminal can have commands which are how the user can interact with the console. Each command can have multiple command syntaxes, which can be defined via a command syntax builder. A command syntax must specify a name for the command, followed by any amount of command arguments and finally and executor. Command arguments are a general interface which take in a string and spit a formatted argument. For example, an Integer Argument takes in a string such as “10” and parses it returning the number 10. Any errors while parsing immediately exits the command and displays a command error in the console. An executor takes in all the arguments along with the headless instances and calls the respective execute method in the respective command class. The terminal thread continuously polls for input and executes the command when needed. This may seem overcomplicated, but it makes it very easy to add commands in the future as we only need to specify a name and the types of arguments, the rest will be handled automatically. 

 

## Inventory 

The player inventory and items are sent in the setContainerContentS2CPacket. The Window ID is always 0 when the current container is the player inventory, this lets us filter out containers we don’t care about. The State ID and Carried item don’t contain any relevant data so we can safely ignore them for now. The Slot Data is what interests us, it’s an array of slot objects. Slot objects contain an item count for the Item Stack along with an Item ID which defines the type of the item. They additionally contain a list of item NBT components to add and remove from the initial components of the item type. There are over 50 components each with different sizes and some even have variable sizes. These components are used to store enchantments or special item names and such. Currently we simply skip over them and throw an error if an item has a component to remove, however this means that we can’t read the rest of the items because we don’t know where the components start and stop without reading them. This means that at the moment, we can’t read the inventory if any of the items have special properties. This feature will need to be implemented in the future; however this will require a lot of work.  

## Pathfinding  

Originally, I had written the pathfinding algorithm using A* however because the Minecraft world is effectively infinite, massive slowdowns were noticeable and A* often failed to find a solution. I ended up using an existing open-source pathfinding tool called Baritone. Baritone is a Minecraft mod that adds pathfinding commands to Minecraft, this means that it is also written in Java. It uses a modified version of A* to accommodate differences between the environment A* was written for and our infinite 3D world. Baritone also includes many other features such as following entities, and mining blocks automatically but most importantly the modified A* pathfinding is extremely quick. For our implementation, I only used the pathfinding algorithm as that was written in pure Java, the rest of the features either were not useful, or I would implement myself. A lot of refactoring was necessary to integrate it into a Java project rather than a Minecraft mod. The Baritone algorithm modifies the A* algorithm, the main problem is that the heuristic is often very far away from the actual distance and as mentioned earlier this makes A* inadmissible and very inefficient. The Baritone algorithm stores the base cost using an incremental back off system. It creates a list of around 6 coefficients ranging from 1 to 10. When calculate the cost of the node, it instead uses this formula: 

f(n) = g(n) / c(i) + h(n) 

c(i) is the coefficient given a certain index. The coefficients start at the first value (index 1) and increases to the next index until the algorithm returns a path further than 5 blocks away from the starting position. This gives the heuristic a greater effect while still paying attention to the cost, because otherwise, A* always believes the goal is nearby and therefore stops caring about the cost, giving an inadmissible (optimal) path. The cost of traveling from one node to another is simply the time in ticks it takes to travel that distance. This means that to add other moves, we can simply create a move (for example mining a block) and set its cost (how long it will take to mine). This also lets us increase the cost based on where the node is. For example, if the node would make the player float, the cost is multiplied by 5 to discourage floating unless necessary. Additionally, the entire path or chunks the path goes through may not be loaded at once. In this case, the clients can’t compute the entire path, they simply make a guess and head towards the correct direction. Once they finish their path, new chunks have been revealed and they can either make another guess or path to the goal if in range. I have created a short video showing off the current state of pathfinding and multi-instancing. The sporadic head movements are intended (they are test movements to confirm the client is still alive). Each instance computes its own path, but they all individually determined and used the same path. 

 

## Bibliography 
“A* Search Algorithm” --- “Deflate” --- “Dijkstra’s Algorithm” --- “Signedness” --- “IEEE_754” Wikipedia, Wikimedia Foundation, 27 Sept. 2024, en.wikipedia.org/wiki  

 
