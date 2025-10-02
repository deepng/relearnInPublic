

# Vibe coding
https://www.youtube.com/watch?v=fHWFF_pnqDk

1. Be claude PM - Ask what you can do for Claude an not what claude can do for you
2. Leaf nodes, not architecture
3. Verifiable output
4. Remember the exponential

`Forget the code even exists`
Think of claude as a junior engineer

`Caveat`: Tech debt can't be vibe coded away. So concentrate on the leaf nodes.
Since they don't have dependencies, they can be vibe coded away. Remember that
the core architecture still needs to be xplored

`How to succeed at vibe coding`
What guidance or context would a new employee need to get started?
Spend 15-20 mins where you write down all the context, and
then hand it off to Claude
You need to be able to ask the right questions
Be a product manager for claude and solve problems for your agents for vibe coding
Carefully design stress tests for stability
Design for human readable and easily verified output

`Security`
Should know what questions to ask
Or have systems which are offline

`Products should be designed for vibe coding`
Example claude artificats
Like solve for hosting with payments and auth taken care of

`Test driven development`
It will help claude to know what to do. Give claude examples of tests to the AI

`Embrace the exponentials`
What does this mean?
Idea of exponential is that they will get better way faster than we can think of
Things get better very fast
The models will get million times faster and smarter in 20 years


`Which workflows to use`
Compact when you know that claude has got to a decent state?
Use claude code as well as cursor. 
Start with claude code and then fix things with cursor
Before starting to write the feature, ask claude to explore the codebase
Figure out what is happening before you vibe code


# Best practices
https://youtu.be/gv0WHhKelSE?t=883

Use ClaudeCode.md

Permission management - curate allowed tools, and

Integration setup - Install tools and

Context Management

`Effective Workflows`

Planning and TODOs

Smart vibe coding

Use screenshots for guide & context

`Advanced Techniques`

Multi-claude && parallelization

Use escape - Pressing escape to stop generation.
Pressing escape twice will jump back in your conversation

Tool expansion & MCP

Headless automation - In CI/CD pipelines




# Prompting best practices
https://www.youtube.com/watch?v=ysPbXH0LpIE

`Practice prompt engineering`
Programming in natural language

Good prompts for a chatbot will have these elements
1. Task context
2. Tone context
3. Background data, documents and Images
4. Detailed task descriptions and rules
5. Examples
6. Conversation history
7. Immediate task description or request (Avoids hallucination)
8. Think step by step / take a deep braath
9. output formatting
10. Prefilled responses (if any)
11. Extended thinking 


Analyze some images and get factual descriptions
You can use anthropic workbench


Engineer preliminary prompts
- Add Task context for clarity
- Add delimiters for clarity and XML tags for structure.
- XMLs are better because the boundaries are clear and its token efficient
Test prompts against cases
Refine prompts iteratively

Bake in some examples in your system prompts
Examples act as templates, especially in tasks that need consistent formatting

Ask AI to answer only if its confident, for examples say that you need an answer only if its 95% confident.

have a tag called <final_verdict> to indicate the final answer


## prompting for agents
https://www.youtube.com/watch?v=XSZP9GhhuAc
When you should be using an agent?
1. Do you know how to do the task step by step?
2. Is this a high value task? Which might generate revenue or save money?
3. Are the parts of the task doable by the agent?
4. How easy is it to discover errors? (If there is high probability of errors, then don't use agents)


Tasks for agents
1. Coding ($$$)
2. Data analysis ($$)
3. Search ($$)
4. Computer use ($)

Key principles of prompting for agents
1. Think like your agent
2. Give agent reasonable heuristics (Don't make irreservable changes, 
3. say stop when you find the answer, make sure the model is budgetted.
4. Tool selection is key - (Tell it what tools to use and when, for example you can search slack instead of searching the web)
5. Guide the thinking process - (For example tell them that all web results may not be true)
6. Most changes will have unintended side effects, be prepared
7. Help the agent manage the context window (Compress context and share it with the next agent if you are running out of tokens)


Good tool:
1. Simple & Accurate tool name
2. Test your tools