# Social Behaviour Regulation using Language Models
*This is a fork of [PROMISE](https://github.com/zhaw-iwi/promise), an application development **framework** that supports the development of complex **language-based interactions** using **state machine modelling** concepts.*

This fork extends the original framework with the means to use language models for social behaviour regulation. This extension tailors the agent's behaviour to the user, making interactions more personalized and dynamic.

## Table of Contents
- [0 Introduction](#0-introduction)
- [1 Behaviour Regulation with the Zurich Model of Social Motivation](#1-behaviour-regulation-with-the-zurich-model-of-social-motivation)
    - [1.1 Example Interaction](#11-example-interaction)
- [2 Behaviour Regulation with Big Five Personality Traits](#2-behaviour-regulation-with-big-five-personality-traits)
    - [2.1 Example Interaction](#21-example-interaction)
- [3 ADHS Coaching](#3-behaviour-regulation-for-adhs-coaching)
    - [3.1 Example Interaction](#31-example-interaction)
- [4 Prototyping](#4-prototyping)
    - [4.1 Deploy and Test Conversational Interactions](#41-deploy-and-test-conversational-interactions)
    - [4.2 Create your own Conversational Interaction](#42-create-your-own-conversational-interaction)
    - [4.3 Create your own Regulation Variation](#43-create-your-own-regulation-variation)
        - [4.3.1 Detection](#431-detection)
        - [4.3.2 Behaviour](#432-behaviour)
        - [4.3.3 Mapping from Detection to Behaviour](#433-mapping-from-detection-to-behaviour)
    - [4.3 Create your own Behaviour Regulation](#44-create-your-own-behaviour-regulation)
- [5 Conclusion](#conclusion)

## 0 Introduction
Language models have become a key tool for the development of conversational AI that enables natural language interactions. However, creating complex conversational experiences where multiple phases of interaction smoothly transition into each other depending on the course of the conversation is a challenge. A single prompt statement often proves unreliable for implementing such dynamic behavior. To solve this problem, we introduced [PROMISE](https://github.com/zhaw-iwi/promise), a framework for dynamic prompt orchestration. PROMISE breaks down complex behaviors into smaller, more precise and reliable prompts that are dynamically selected and assembled to match the interaction flow.

However, a further challenge arises when the system has to adapt several types of behaviors at the same time. For example, in a coaching scenario with the aim of improving health literacy and treatment adherence, the tone of voice needs to be adapted - whether through the use of different persuasion strategies, empathy or cognitive techniques. If the system is able to recognize characteristics of the user, e.g. as with Theory of Mind, it can dynamically adapt its social behavior and thus improve the effectiveness of the interaction. To address this challenge, we propose an extension of PROMISE for social behavior regulation that allows multiple types of behavioral variations to be dynamically and independently adapted, ensuring a more nuanced and effective conversational experience.

## 1 Behaviour Regulation with the Zurich Model of Social Motivation

While we are experimenting with different approaches to behaviour regulation and therefore aim to support exchangeability, our first attempt was based on the [Zurich Model of Social Motivation](https://de.wikipedia.org/wiki/Z%C3%BCrcher_Modell_der_sozialen_Motivation) by [Norbert Bischoff](https://bischof.com/) as described in [Zeischrift für Psychologie, 1993](https://doi.org/10.5282/ubm/epub.2852) and shown in the following figure.

<picture>
 <img alt="Zurich Model of Social Motivation" src=".doc/ZurichModel.png">
</picture>

The Zurich Model is a framework for understanding human motivation based on three core dimensions: dependency, enterprise, and autonomy.

### 1.1 Example Interaction

The following figure shows a conversational interaction between a user and an assistant regulating its social behaviour. The user is simulated using GPT instructed to gaslight the assistant according to a gaslighting specification taken from [HackSpirit](https://hackspirit.com/gaslighting-in-relationships/).

The internal state consisting of dependency (D), enterprise (E) and autonomy (A) deficit (-1) or excess (1), and the resulting social behaviour are indicated in the two columns on the right.

The social behaviour attempting to address deficits or excesses consist of prompt extensions that are added to the state prompt dynamically. As a result, social behaviour regulation can be applied to any basic behaviour and will be adapted dynamically during an interaction.

<picture>
 <img alt="Assistant regulating its social behaviour" src=".doc/AssistantRegulation.png">
</picture>

For comparison, the following figure shows the same user interacting with a non-regulated assistant with the same basic behaviour. It can be observed that GPT tends to behave more defencively as the regulating assistant shown in the figure above. The regulated assistant is less likely to fall for gaslighting.

<picture>
 <img alt="Assistant regulating its social behaviour" src=".doc/NoRegulation.png">
</picture>

## 2 Behaviour Regulation with Big Five Personality Traits

As an alternative approach to social behaviour regulation, we implemented **BigFiveImpl** which detects the user personality traits according to [Big Five personality traits](https://en.wikipedia.org/wiki/Big_Five_personality_traits). The conversation with the user is continuously analysed for information about the following traits.
- Openness to experience (inventive/curious vs. consistnt/cautious)
- Consientiousness (efficient/organised vs. extravagant/careless)
- Extraversion (outgoing/energetic vs. solitary/reserved)
- Agreeableness (friendly/compassionate vs. critical/judgemental)
- Neuroticism (sensitive/nervous vs. resilient/confident)

These detections are then mapped to the behaviours (aggression, supplication, exploration, avoidance and affiliation) of the Zurich Model described above as follows.
- Aggression = Extraversion × (- Agreeableness)
- Supplication = Agreeableness x (- Conscientiousness)
- Exploration = Openness x Extraversion
- Avoidance = (- Openness) x Neuroticism
- Affiliation = Agreeableness x Neuroticism

As a result, behaviour is computed as a function of the user's Big Five personality traits. The result of these computations determines whether the agent should exhibit positive, negative, or neutral behaviour for each emotion.

For example, if the user is high in openness (1) and extraversion (1), the product for exploration will be 1, prompting the agent to engage in exploratory conversation. Conversely, if the user is low in openness (-1) and high in neuroticism (1), the product for avoidance will be 1, meaning the agent will avoid unfamiliar topics.

### 2.1 Example Interaction

We used GPT to act as a fictitious user that has the following Big Five personality traits:
- Openness: -1 (Low, prefers familiar topics, dislikes novelty)
- Conscientiousness: 1 (High, values structure, organization, and reliability)
- Extraversion: -1 (Low, reserved, introverted, prefers quieter conversations)
- Agreeableness: 1 (High, cooperative, compassionate, and avoids confrontation)
- Neuroticism: -1 (High, prone to anxiety, emotional sensitivity, and seeks reassurance)

In the following figure, the traits detected and the resulting behavioural modulations are shown in the two columns on the right.
<picture>
 <img alt="Assistant regulating its social behaviour to comfort Big Five traits" src=".doc/BigFiveRegulation.png">
</picture>

## 3 Behaviour Regulation for ADHS Coaching

To explore novel applications, we implemented **CoachingImpl** which detects the user's issues and regulates its behaviour such as to coach the user. The conversation with the user is continuously analysed for information about the following issues.
- Focus Level
- Emotional Stability
- Restlessness Level
- Time Management
- Social Interaction

These detections are then mapped to the behaviours (aggression, supplication, exploration, avoidance and affiliation) of the Zurich Model described above as follows.
- Empathetic Encouragement = (- Emotional Stability)
- Grounding Mentoring = Focus Level x Restlessness
- Productivity Reinforcement = Focus Level x Time Management
- Reflection and Self-Regulation Faciliation = Emotional Stability x Time Management
- Active Listening = (- Social Interaction)

As a result, coaching behaviour is computed as a function of the user's issues. The result of these computations determines if and which coaching behaviours the agent performs.

### 3.1 Example Interaction

We used GPT to act as a fictitious user that has the following issues:
- Focus Level: -1 (currently distracted and having difficulty focusing)
- Emotional Stability: -1 (emotionally distressed, feeling frustrated with their current situation)
- Restlessness Level: 1 (physically calm at the moment, not experiencing restlessness)
- Time Management: -1 (struggling with time management, feeling behind on tasks)
- Social Interaction: 0 (no recent social interactions mentioned or relevant)

In the following figure, the issues detected and the resulting coaching behaviours are shown in the two columns on the right.
<picture>
 <img alt="ADHS coaching adapting the coaching behaviours to issues detected" src=".doc/ADHSCoaching.png">
</picture>

## 4 Prototyping

*A complete introduction to the [PROMISE](https://github.com/zhaw-iwi/promise) framework including a guide on how to get framework applications up and running is required to understand and use this framework extension.*

### 4.1 Deploy and Test Conversational Interactions

Use the documentation of [PROMISE](https://github.com/zhaw-iwi/promise) to deploy existing conversational interactions. As part of this repository, you will find the following examples.
- **ZurichModelAssistant** The basic behaviour regulated according to the Zurich Model.
- **ZurichModelTheoryOfMind** The basic behaviour regulated according to the Zurich Model where the detections reflect the user motivations, and the behaviours aim to accommodate the user motivations.
- **BigFiveRegulation** The basic behaviour regulated based on Big Five personality traits detections mapped to the Zurich Model behaviours set to comfort the specific user traits.
- **ADHS Coaching** A coaching behaviour that is regulated based on user issues detected.
- **BaselineUnregulated** A basic behaviour that is unregulated, reflecting the default language model behaviour.

All of them share the exact same basic behaviour (state prompt and transition). All of them can be found at src/test/java/ch.zhaw.statefulconversation.bots.

You may use ChatGPT to simulate a user with a specific conversational behaviour. Compare the resulting detections and behaviours between different user behaviours. Compare the regulated behaviour to the baseline interaction.

### 4.2 Create your own Conversational Interaction

Using the existing interaction implementations listed above as a template, create your own interaction. A **State** regulating its social behaviour according to detections created as follows.

```
RegulationSystem regulationSystem = new ZurichModelImpl(0, 0, 0);

// ...

State state = new RegulatedState(
    regulationSystem,
    "As a digital therapy coach, check in with your patient...",
    "Check-In Interaction",
    "...compose a single, very short message to initiate...",
    List.of(transition)
);
```
where the **RegulationSystem** interface implementations such as **ZurichModelImpl**, **BigFiveImpl** or **CoachingImpl** encapsulate all the regulation functionality (detection, mapping, behaviour).

The behaviour regulation can be exchanged by adapting the first line of code above as exemplified with the following adaptation for the use of the Big Five regulation. 
```
RegulationSystem regulationSystem = new BigFiveImpl();
```

Once you created your own conversational interaction, deploy and test it as described above. 

### 4.3 Create your own Regulation Variation

#### 4.3.1 Detection

*In what follows, the detection mechanism is explained using the example of Big Five regulation. The conceptual mechanism is the same for all regulation approaches.* 

The detections of personality traits are implemented as requests to the language model resulting in responses reflecting the presence (1), absence (-1), or lack of evidence (0) of each trait. The requests include prompts that are provided by a **PromptsProvider** implementation. By default, the **DefaultPromptsPovider** is used.

**PromptsProvider** implementations need to implement (override) the following methods related to personality traits detections. These methods are expected to return a prompt instructing the language model to analyse the conversation held with the user so far, and to decide whether the particular trait is present (1), absent (-1), or whether there is no evidence (0) regarding this trait.
- detectOpenness(): String
- detectConscientiousness(): String
- detectExtraversion(): String
- detectAgreeableness(): String
- detectNeuroticism(): String

If an alternative **PromptsProvider** implementation is created and should be used, the following line of code can be added as a new first line of code in the **setUp()** method of the unit test (e.g., in **BigFiveRegulation** in src/test/java/ch.zhaw.statefulconveration.bots).
```
PromptsProvider.instanciate([Name of your alternative provider class].class);
```

#### 4.3.2 Behaviour

*In what follows, the behaviour modulation mechanism is explained using the example of Big Five regulation. The conceptual mechanism is the same for all regulation approaches.* 

The behaviors chosen based on the detected personality traits are extensions to the state prompt. Responses to the user are therefore generated by the language model by prompting the model with the concatenation of the state prompt with all behavioural prompt extensions selected based on the personality traits detected. The behavioural prompt extensions are also provided by the **PromptsProvider** implementation. By default, the **DefaultPromptsProvider** is used.

**​PromptsProvider** implementations need to implement (override) the following methods related to behavioural prompt extensions. These methods are expected to return a prompt extension instructing the language model to adapt its conversational behaviour when generating the next message to the user.
- behaveAggression(): String
- behaveAggressionNot(): String
- behaveAggressionNeutral(): String
- behaveSupplication(): String
- behaveSupplicationNot(): String
- behaveSupplicationNeutral(): String
- behaveExploration(): String
- behaveExplorationNot(): String
- behaveExplorationNeutral(): String
- behaveAvoidance(): String
- behaveAvoidanceNot(): String
- behaveAvoidanceNeutral(): String
- behaveAffiliation(): String
- behaveAffiliationNot(): String
- behaveAffiliationNeutral(): String

If an alternative **PromptsProvider** implementation is created and should be used, the following line of code can be added as a new first line of code in the **setUp()** method of the unit test (e.g., in **BigFiveRegulation** in src/test/java/ch.zhaw.statefulconveration.bots).
```
PromptsProvider.instanciate([Name of your alternative provider class].class);
```

#### 4.3.3 Mapping from Detection to Behaviour

*In what follows, the mapping mechanism is explained using the example of Big Five regulation. The conceptual mechanism is the same for all regulation approaches.* 

The mapping of personality traits detected to behaviour modulations is implemented in the **BigFiveImpl** class and its **process(Utterances): String** method. The default implementation of this mapping is [described above](#behaviour-regulation-with-big-five-personality-traits) and is meant to be computed as a product of confirmed, negated or absent personality traits. The **process(Utterances): String** method is expected to return the complete concatenation of all prompt extensions to be appended to the state prompt.

The default mapping may be adapted in the existing code of this method. If interchangeability is required, subclasses of **BigFiveImpl** may be created, where the **process(Utterances): String** methods are overriden.

### 4.4 Create your own Behaviour Regulation

Using the existing behaviour regulation implementations as a template, create your own behaviour regulation. All framework extensions related to social behaviour regulation are located at src/main/java/ch.zhaw.statefulconversation.socialbehaviourregulation.

To create your own behaviour regulation, you will need to design and implement the following classes.
- **YourRegulationImpl extends RegulationSystem** where the Detectors and Actuators are specified and initialised, and where the **process(Utterances): String** method specifies the detection and behaviour logics.
- **PromptsProvider** where you specify all the prompts for detections and behaviours. Implement this as an abstract class if you want to experiment with different variants of these prompts.
- **APromptsProvider**, **BPromptsProvider**, ... if you left the **PromptsProvider** abstract and want to have variants of prompts to experiment with.
- **BehaviourImpl implements Behaviour** where the complete behaviour regulation prompt extension is assembled. Note that this is just for convenience and it is not an @Entity.

Once you created these classes, create your own conversational interaction as described above.

## 5 Conclusion
This extension of the PROMISE framework introduces a flexible means of regulating social behavior in conversational interactions using language models. By leveraging psychological models such as the Zurich Model of Social Motivation and Big Five Personality Traits, interactions become more personalized, adaptive, and contextually aware. As conversational AI continues to evolve, these regulation systems offer pathways for more human-like and emotionally intelligent engagements, with potential for applications in digital therapy, coaching, education, or customer service. 

We are currently using this to explore further refinements and additional behavioral models to add social skills to conversational interactions.